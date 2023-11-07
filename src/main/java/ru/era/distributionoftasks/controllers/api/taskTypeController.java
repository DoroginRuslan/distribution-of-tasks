package ru.era.distributionoftasks.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.era.distributionoftasks.entities.TaskType;
import ru.era.distributionoftasks.services.GradeService;
import ru.era.distributionoftasks.services.TaskTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/task-types")
public class taskTypeController {
    @Autowired
    TaskTypeService taskTypeService;

    @GetMapping("")
    public List<TaskType> getAllGrades() {
        return taskTypeService.getAll();
    }

    @PostMapping("")
    public TaskType addGrade(@RequestBody TaskType taskType) {
        return taskTypeService.addGrade(taskType);
    }

    @PutMapping("")
    public List<TaskType> updateGrade(@RequestBody List<TaskType> taskTypes) {
        return taskTypeService.updateGrades(taskTypes);
    }

    @DeleteMapping("")
    public void deleteAllGrades() {
        taskTypeService.deleteAllGrades();
    }

    @GetMapping("/{taskTypeId}")
    public TaskType getGrade(@PathVariable Long taskTypeId) {
        return taskTypeService.getGrade(taskTypeId);
    }

    @PutMapping("/{taskTypeId}")
    public TaskType updateGrade(@RequestBody TaskType taskType, @PathVariable Long taskTypeId) {
        return taskTypeService.updateGrade(taskType, taskTypeId);
    }

    @DeleteMapping("/{taskTypeId}")
    public void deleteGrade(@PathVariable Long taskTypeId) {
        taskTypeService.deleteGrade(taskTypeId);
    }

}
