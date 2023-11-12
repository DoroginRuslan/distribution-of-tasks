package ru.era.distributionoftasks.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.era.distributionoftasks.graphhopper.GraphhopperErrorException;

import java.util.NoSuchElementException;

// TODO: 09.11.2023 Добавить нормальное логгирование
@ControllerAdvice
public class DefaultAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({NoSuchElementException.class, GraphhopperErrorException.class})
    public ResponseEntity<Response> handleException(RuntimeException e) {
        e.printStackTrace();
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
