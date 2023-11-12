package ru.era.distributionoftasks.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.era.distributionoftasks.entities.TaskType;

public interface TaskTypeRepository extends CrudRepository<TaskType, Long> {
    TaskType findByName(String name);
}
