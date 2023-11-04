package ru.era.distributionoftasks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.era.distributionoftasks.entities.Employee;
import ru.era.distributionoftasks.repositories.EmployeeRepository;

@RestController
public class HelloWorldController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/addEmployee")
    public String addEmployee() {
        Employee employee = new Employee();
        employee.setFio("Дерягин Никита Владимирович");
        employee.setAddress("Краснодар, Красная, д. 139");
        employee.setGrade("Синьор");
        employeeRepository.save(employee);
        return employee.toString();
    }
}
