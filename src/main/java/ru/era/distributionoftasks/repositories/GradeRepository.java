package ru.era.distributionoftasks.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.era.distributionoftasks.entities.Grade;

public interface GradeRepository extends CrudRepository<Grade, Long> {
}
