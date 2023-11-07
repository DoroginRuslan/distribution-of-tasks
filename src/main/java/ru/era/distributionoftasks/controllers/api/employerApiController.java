package ru.era.distributionoftasks.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.era.distributionoftasks.entities.Employee;
import ru.era.distributionoftasks.services.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("api/employees")
public class employerApiController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping(value = "")
    public List<Employee> getEmployers() {
        return employeeService.getAllEmployers();
    }

    @PostMapping("")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployer(employee);
    }

    @PutMapping("")
    public List<Employee> updateEmployers(@RequestBody List<Employee> employees) {
        return employeeService.updateEmployers(employees);
    }

    @DeleteMapping("")
    public void deleteAllEmployees() {
        employeeService.deleteAllEmployees();
    }

    @GetMapping("/{employeeId}")
    public Employee getEmployee(@PathVariable Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PutMapping("/{employeeId}")
    public Employee updateEmployer(@RequestBody Employee employee, @PathVariable Long employeeId) {
        return employeeService.updateEmployer(employee, employeeId);
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}
