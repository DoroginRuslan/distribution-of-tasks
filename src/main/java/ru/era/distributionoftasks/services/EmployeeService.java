package ru.era.distributionoftasks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.entities.Employee;
import ru.era.distributionoftasks.entities.Grade;
import ru.era.distributionoftasks.repositories.EmployeeRepository;
import ru.era.distributionoftasks.repositories.GradeRepository;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    GradeRepository gradeRepository;

    public Employee getEmployee(long id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    public List<Employee> getAllEmployers() {
        return (List<Employee>) employeeRepository.findAll();
    }

    public List<Employee> getActiveEmployees() {
        return employeeRepository.findByIsActive(true);
    }

    public Employee addEmployer(Employee employee) {
        Grade grade = gradeRepository.findById(employee.getGrade().getId()).orElseThrow();
        employee.setGrade(grade);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployer(Employee employee, Long employeeId) {
        employee.setId(employeeId);
        employee.setGrade(gradeRepository.findById(employee.getGrade().getId()).orElseThrow());
        return employeeRepository.save(employee);
    }

    public List<Employee> updateEmployers(List<Employee> employees) {
        return (List<Employee>) employeeRepository.saveAll(employees);
    }

    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

    public List<Employee> deactivateAllEmployees() {
        employeeRepository.setIsActiveForAll(false);
        return (List<Employee>) employeeRepository.findAll();
    }

    public Employee deactivateEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        employee.setActive(false);
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }


}
