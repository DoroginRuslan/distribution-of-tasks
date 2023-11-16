package ru.era.distributionoftasks.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.era.distributionoftasks.entities.UnallocatedTask;

public interface UnallocatedTaskRepository extends CrudRepository<UnallocatedTask, Long> {

}
