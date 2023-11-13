package ru.era.distributionoftasks.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.era.distributionoftasks.entities.Employee;
import ru.era.distributionoftasks.entities.TaskLog;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskLogRepository extends CrudRepository<TaskLog, Long> {

    @Query("select t from TaskLog t where t.employee = :employee and DATE_TRUNC('day', t.taskSetDate) = :localDate order by t.id")
    List<TaskLog> findDateTasksForEmployee(Employee employee, LocalDateTime localDate);
}
