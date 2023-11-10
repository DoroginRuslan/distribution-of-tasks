package ru.era.distributionoftasks.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.era.distributionoftasks.repositories.GradeRepository;


class TaskTypeServiceTest {
    @Autowired GradeRepository gradeRepository;

    @Test
    void getDailyTasksForEmployee() {
        System.out.println(gradeRepository.findAll());
    }
}