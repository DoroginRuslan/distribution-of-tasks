package ru.era.distributionoftasks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.era.distributionoftasks.entities.*;
import ru.era.distributionoftasks.repositories.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
public class HelloWorldController {
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private GradeRepository gradeRepository;
    @Autowired private BankRepository bankRepository;
    @Autowired private TaskTypeRepository taskTypeRepository;
    @Autowired private TaskLogRepository taskLogRepository;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/addEmployee")
    public String addEmployee() {
        Employee employee = new Employee()
                .setFio("Дерягин Никита Владимирович")
                .setAddress("Краснодар, Красная, д. 139")
                .setGrade(gradeRepository.findById(1L).orElse(null));
        employeeRepository.save(employee);
        return employee.toString();
    }

    @GetMapping("/getEmployers")
    public String getEmployers() {
        return getDataFromRepoByLines(employeeRepository);
    }

    @GetMapping("/addGrade")
    public String addGrade() {
        Grade grade = new Grade()
                .setName("Синьор")
                .setRank(0);
        gradeRepository.save(grade);
        return grade.toString();
    }

    @GetMapping("/getGrades")
    public String getGrades() {
        return getDataFromRepoByLines(gradeRepository);
    }

    @GetMapping("/addBank")
    public String addBank() {
        Bank bank = new Bank()
                .setMaterialsDelivered(true)
                .setApprovedApplicationsNum(3)
                .setLastCardIssuanceDays(4)
                .setIssuanceCardsNum(20)
                .setRegistrationDate("Давно")
                .setAddress("г. Краснодар, ул. Печкина, д. 1");
        bankRepository.save(bank);
        return bank.toString();
    }

    @GetMapping("/getBanks")
    public String getBanks() {
        return getDataFromRepoByLines(bankRepository);
    }

    @GetMapping("/addTaskType")
    public String addTaskType() {
        TaskType taskType = new TaskType()
                .setPriority(0)
                .setTimeReq(LocalTime.of(4, 40))
                .setName("Точка подключена вчера");
        taskTypeRepository.save(taskType);
        return taskType.toString();
    }

    @GetMapping("/getTaskTypes")
    public String getTaskTypes() {
        return getDataFromRepoByLines(taskTypeRepository);
    }

    @GetMapping("/addTaskLog")
    public String addTaskLog() {
        TaskLog taskLog = new TaskLog()
                .setEmployee(employeeRepository.findById(1L).orElse(null))
                .setTaskType(taskTypeRepository.findById(1L).orElse(null))
                .setTaskSetDate(LocalDateTime.now())
                .setCompleted(false)
                .setCommentary("Съел шаурму на вокзале и недоехал");
        taskLogRepository.save(taskLog);
        return taskLog.toString();
    }

    @GetMapping("/getTaskLog")
    public String getTaskLog() {
        return getDataFromRepoByLines(taskLogRepository);
    }
    
    private static String getDataFromRepoByLines(CrudRepository<?, ?> repo) {
        StringBuilder sb = new StringBuilder();
        for(Object o : repo.findAll()) {
            sb.append(o.toString()).append("<br>");
        }
        return sb.toString();
    }
}
