package ru.era.distributionoftasks.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.era.distributionoftasks.entities.Employee;
import ru.era.distributionoftasks.services.EmployeeService;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class employerApiController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping(value = "/employers")
    public List<Employee> getEmployers() {
        return employeeService.getAllEmployers();
    }

    @GetMapping(value = "/employee/{employeeId}")
    public Employee getEmployee(@PathVariable Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping(value = "/employee/add")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployer(employee);
    }
}
