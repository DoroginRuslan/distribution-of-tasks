package ru.era.distributionoftasks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.entities.TaskType;
import ru.era.distributionoftasks.repositories.GradeRepository;
import ru.era.distributionoftasks.repositories.TaskTypeRepository;

import java.util.List;

@Service
public class TaskTypeService {
    @Autowired
    TaskTypeRepository taskTypeRepository;

    @Autowired
    GradeRepository gradeRepository;

    public List<TaskType> getAll() {
        return (List<TaskType>) taskTypeRepository.findAll();
    }

    public TaskType addGrade(TaskType taskType) {
        taskType.setGrade(gradeRepository.findById(taskType.getGrade().getId()).orElseThrow());
        return taskTypeRepository.save(taskType);
    }

    public List<TaskType> updateGrades(List<TaskType> taskTypes) {
        return (List<TaskType>) taskTypeRepository.saveAll(taskTypes);
    }

    public void deleteAllGrades() {
        taskTypeRepository.deleteAll();
    }

    public TaskType getGrade(Long taskTypeId) {
        return taskTypeRepository.findById(taskTypeId).orElseThrow();
    }

    public TaskType updateGrade(TaskType taskType, Long taskTypeId) {
        taskType.setGrade(gradeRepository.findById(taskType.getGrade().getId()).orElseThrow());
        return taskTypeRepository.save(taskType.setId(taskTypeId));
    }

    public void deleteGrade(Long taskTypeId) {
        taskTypeRepository.deleteById(taskTypeId);
    }
}
