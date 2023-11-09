package ru.era.distributionoftasks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.era.distributionoftasks.entities.*;
import ru.era.distributionoftasks.repositories.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

// TODO: 09.11.2023 Не забыть удалить его после тестирования 
@RestController()
@RequestMapping("/api/test")
public class TestApiController {
    @Autowired
    BankRepository bankRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    GradeRepository gradeRepository;
    @Autowired
    TaskLogRepository taskLogRepository;
    @Autowired
    TaskTypeRepository taskTypeRepository;

    @GetMapping("/prepare-repo")
    public List<TaskLog> prepareRepo() {
        clearAll();
        Grade grade = gradeRepository.save(new Grade()
                .setName("Миддл")
                .setRank(1));
        Employee employee1 = employeeRepository.save(new Employee()
                .setFio("Иванов Иван Иваныч")
                .setGrade(grade)
                .setAddress("Проспект Виноградный")
                .setActive(true));
        Employee employee2 = employeeRepository.save(new Employee()
                .setFio("Петров Пётр Петрович")
                .setGrade(grade)
                .setAddress("Проспект Шоколадный")
                .setActive(true));
        Bank bank = bankRepository.save(new Bank()
                .setAddress("Улица Бородатых")
                .setMaterialsDelivered(true)
                .setRegistrationDate("Давно")
                .setIssuanceCardsNum(5));
        LocalDateTime today = LocalDateTime.now();
        TaskType taskType = taskTypeRepository.save(new TaskType()
                .setPriority(0)
                .setTimeReq(LocalTime.of(4, 0))
                .setName("Задача")
                .setGrade(grade)
        );
        TaskLog taskLog1 = taskLogRepository.save(new TaskLog()
                .setEmployee(employee1)
                .setTaskSetDate(today)
                .setCompleted(false)
                .setCommentary("Сегодняшняя задача для сотрудника 1, не выполнена")
                .setBank(bank)
                .setTaskType(taskType)
        );
        TaskLog taskLog2 = taskLogRepository.save(new TaskLog()
                .setEmployee(employee1)
                .setTaskSetDate(today.minusDays(1))
                .setCompleted(false)
                .setCommentary("Вчерашняя задача для сотрудника 1, не выполнена")
                .setBank(bank)
                .setTaskType(taskType)
        );
        TaskLog taskLog3 = taskLogRepository.save(new TaskLog()
                .setEmployee(employee1)
                .setTaskSetDate(today)
                .setCompleted(true)
                .setCommentary("Сегодняшняя задача для сотрудника 1, выполнена")
                .setBank(bank)
                .setTaskType(taskType)
        );
        TaskLog taskLog4 = taskLogRepository.save(new TaskLog()
                .setEmployee(employee2)
                .setTaskSetDate(today)
                .setCompleted(false)
                .setCommentary("Сегодняшняя задача для сотрудника 2, не выполнена")
                .setBank(bank)
                .setTaskType(taskType)
        );
//        clearAll();
//        return Arrays.asList(grade, employee1, employee2, bank, today, taskType, Arrays.asList(
//                taskLog1, taskLog2, taskLog3, taskLog4
//        ));

        return Arrays.asList(taskLog1, taskLog2, taskLog3, taskLog4);
    }

    private void clearAll() {
        Stream.of(bankRepository, employeeRepository, gradeRepository, taskLogRepository, taskTypeRepository)
                .forEach(CrudRepository::deleteAll);
    }

}
