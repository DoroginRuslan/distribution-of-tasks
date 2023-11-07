package ru.era.distributionoftasks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.entities.Employee;
import ru.era.distributionoftasks.repositories.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {
    // TODO: 07.11.2023 Настроить транзакции
    @Autowired EmployeeRepository employeeRepository;

    public Employee getEmployee(long id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    public List<Employee> getAllEmployers() {
        return (List<Employee>) employeeRepository.findAll();
    }

    public Employee addEmployer(Employee employee) {
        return employeeRepository.save(employee);
    }
}
