package ru.era.distributionoftasks.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.era.distributionoftasks.entities.TaskLog;
import ru.era.distributionoftasks.services.TaskLogService;

import java.util.List;

@RestController
@RequestMapping("/api/task-logs")
public class taskLogController {
    @Autowired
    TaskLogService taskLogService;

    @GetMapping("")
    public List<TaskLog> getAllTaskLogs() {
        return taskLogService.getAll();
    }

    @PostMapping("")
    public TaskLog addTaskLog(@RequestBody TaskLog taskType) {
        return taskLogService.addTaskLog(taskType);
    }

    @PutMapping("")
    public List<TaskLog> updateTaskLog(@RequestBody List<TaskLog> taskTypes) {
        return taskLogService.updateTaskLogs(taskTypes);
    }

    @DeleteMapping("")
    public void deleteAllTaskLogs() {
        taskLogService.deleteAllTaskLogs();
    }

    @GetMapping("/{taskTypeId}")
    public TaskLog getTaskLog(@PathVariable Long taskTypeId) {
        return taskLogService.getTaskLog(taskTypeId);
    }

    @PutMapping("/{taskTypeId}")
    public TaskLog updateTaskLog(@RequestBody TaskLog taskType, @PathVariable Long taskTypeId) {
        return taskLogService.updateTaskLog(taskType, taskTypeId);
    }

    @DeleteMapping("/{taskTypeId}")
    public void deleteTaskLog(@PathVariable Long taskTypeId) {
        taskLogService.deleteTaskLog(taskTypeId);
    }

    @GetMapping("/daily/employee/{employeeId}")
    public List<TaskLog> getDailyTasksForEmployee(@PathVariable Long employeeId) {
        return taskLogService.getDailyTasksForEmployee(employeeId);
    }
}
