package ru.era.distributionoftasks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.era.distributionoftasks.entities.Employee;
import ru.era.distributionoftasks.repositories.EmployeeRepository;

import java.util.stream.Stream;

@RestController
public class HelloWorldController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/addEmployee")
    public String addEmployee() {
        Employee employee = new Employee()
                .setFio("Дерягин Никита Владимирович")
                .setAddress("Краснодар, Красная, д. 139")
                .setGrade("Синьор");
        employeeRepository.save(employee);
        return employee.toString();
    }

    @GetMapping("/getEmployers")
    public String getEmployers() {
        StringBuilder sb = new StringBuilder();
        for(Employee employee : employeeRepository.findAll()) {
            sb.append(employee.toString()).append("<br>");
        }
        return sb.toString();
    }
}
