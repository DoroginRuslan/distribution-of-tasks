package ru.era.distributionoftasks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.entities.Bank;
import ru.era.distributionoftasks.entities.Employee;
import ru.era.distributionoftasks.entities.TaskLog;
import ru.era.distributionoftasks.repositories.BankRepository;
import ru.era.distributionoftasks.repositories.EmployeeRepository;
import ru.era.distributionoftasks.repositories.TaskLogRepository;
import ru.era.distributionoftasks.repositories.TaskTypeRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        return taskLogRepository.findDateTasksForEmployee(employee, LocalDate.now().atStartOfDay());
    }

    public Map<Bank, Integer> getOverdueTasksWithOverdue(LocalDate today) {
        Map<Bank, Integer> result = new HashMap<>();
        List<TaskLog> overdueTaskList = taskLogRepository.findByIsCompleted(false);
        for(TaskLog overdueTask : overdueTaskList) {
            int overdue = (int) overdueTask.getTaskSetDate().toLocalDate().until(today, ChronoUnit.DAYS);
            result.put(overdueTask.getBank(), overdue);
        }
        return result;
    }
}
