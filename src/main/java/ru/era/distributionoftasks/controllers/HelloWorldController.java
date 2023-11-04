package ru.era.distributionoftasks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(value = "/process")
//    @JsonManagedReference
    public String processFormData(@RequestBody Dattt dat) {
        System.out.println(dat);
        // Process the data or perform any backend tasks
        // ...

        // Return a response back to the frontend
        return "{\"status\": \"success\", \"message\": \"Data received successfully!\"}";
    }
}
