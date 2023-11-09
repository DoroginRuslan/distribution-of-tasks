package ru.era.distributionoftasks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.entities.TaskLog;
import ru.era.distributionoftasks.repositories.BankRepository;
import ru.era.distributionoftasks.repositories.EmployeeRepository;
import ru.era.distributionoftasks.repositories.TaskLogRepository;
import ru.era.distributionoftasks.repositories.TaskTypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskLogService {
    @Autowired
    TaskLogRepository taskLogRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    TaskTypeRepository taskTypeRepository;
    @Autowired
    BankRepository bankRepository;

    public List<TaskLog> getAll() {
        return (List<TaskLog>) taskLogRepository.findAll();
    }

    public TaskLog addTaskLog(TaskLog taskLog) {
        taskLog.setEmployee(employeeRepository.findById(taskLog.getEmployee().getId()).orElseThrow());
        taskLog.setTaskType(taskTypeRepository.findById(taskLog.getTaskType().getId()).orElseThrow());
        taskLog.setBank(bankRepository.findById(taskLog.getBank().getId()).orElseThrow());
        return taskLogRepository.save(taskLog);
    }

    public TaskLog updateTaskLog(TaskLog taskLog, Long taskLogId) {
        taskLog.setEmployee(employeeRepository.findById(taskLog.getEmployee().getId()).orElseThrow());
        taskLog.setTaskType(taskTypeRepository.findById(taskLog.getTaskType().getId()).orElseThrow());
        taskLog.setBank(bankRepository.findById(taskLog.getBank().getId()).orElseThrow());
        return taskLogRepository.save(taskLog.setId(taskLogId));
    }

    public void deleteAllTaskLogs() {
        taskLogRepository.deleteAll();
    }

    public TaskLog getTaskLog(Long employeeId) {
        return taskLogRepository.findById(employeeId).orElseThrow();
    }

    public List<TaskLog> updateTaskLogs(List<TaskLog> taskLogs) {
        return (List<TaskLog>) taskLogRepository.saveAll(taskLogs);
    }

    public void deleteTaskLog(Long taskLogId) {
        taskLogRepository.deleteById(taskLogId);
    }

    public List<TaskLog> getDailyTasksForEmployee(Long employeeId) {
        return new ArrayList<>();
    }
}
