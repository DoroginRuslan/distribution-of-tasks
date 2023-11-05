package ru.era.distributionoftasks.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.era.distributionoftasks.entities.TaskLog;

public interface TaskLogRepository extends CrudRepository<TaskLog, Long> {
}
