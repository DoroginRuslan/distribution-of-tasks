package ru.era.distributionoftasks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.entities.Bank;
import ru.era.distributionoftasks.entities.UnallocatedTask;
import ru.era.distributionoftasks.repositories.UnallocatedTaskRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class UnallocatedTaskService {
    @Autowired
    private UnallocatedTaskRepository unallocatedTaskRepository;

    public Map<Bank, Integer> getUnallocatedTasksWithOverdue(LocalDate today) {
        Map<Bank, Integer> result = new HashMap<>();
        Iterable<UnallocatedTask> unallocatedTaskList = unallocatedTaskRepository.findAll();
        for(UnallocatedTask unallocatedTask : unallocatedTaskList) {
            long daysBetween = unallocatedTask.getDesiredDate().until(today, ChronoUnit.DAYS);
            result.put(unallocatedTask.getBank(), (int) daysBetween);
        }
        return result;
    }
}
