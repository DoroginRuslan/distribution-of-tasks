package ru.era.distributionoftasks.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.era.distributionoftasks.entities.Employee;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findByIsActive(boolean isActive);
    @Query("update Employee e set e.isActive = :activeStatus")
    int setIsActiveForAll(boolean activeStatus);
}
