package ru.era.distributionoftasks.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.era.distributionoftasks.entities.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
}
