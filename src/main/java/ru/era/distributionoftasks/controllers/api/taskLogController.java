package ru.era.distributionoftasks.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.era.distributionoftasks.entities.TaskLog;
import ru.era.distributionoftasks.services.DistributeDalyTasksService;
import ru.era.distributionoftasks.services.TaskLogService;
import ru.era.distributionoftasks.services.distributor.DistributorConnector;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/task-logs")
public class taskLogController {
    @Autowired
    TaskLogService taskLogService;

    @Autowired
    DistributeDalyTasksService distributeDalyTasksService;

    @Autowired
    DistributorConnector distributorConnector;

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
        System.out.println("daf");
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

    @GetMapping("/daily/distribute")
    public List<TaskLog> distributeDailyTasks() {
        return distributeDalyTasksService.distribute(LocalDate.now());
    }
}
