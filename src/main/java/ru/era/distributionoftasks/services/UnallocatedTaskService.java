package ru.era.distributionoftasks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.entities.Bank;
import ru.era.distributionoftasks.entities.UnallocatedTask;
import ru.era.distributionoftasks.repositories.BankRepository;
import ru.era.distributionoftasks.repositories.UnallocatedTaskRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UnallocatedTaskService {
    @Autowired
    private UnallocatedTaskRepository unallocatedTaskRepository;
    @Autowired
    private BankRepository bankRepository;

    public Map<Bank, Integer> getUnallocatedTasksWithOverdue(LocalDate today) {
        Map<Bank, Integer> result = new HashMap<>();
        Iterable<UnallocatedTask> unallocatedTaskList = unallocatedTaskRepository.findAll();
        for(UnallocatedTask unallocatedTask : unallocatedTaskList) {
            long daysBetween = unallocatedTask.getDesiredDate().until(today, ChronoUnit.DAYS);
            result.put(unallocatedTask.getBank(), (int) daysBetween);
        }
        return result;
    }

    public List<UnallocatedTask> findAll() {
        return (List<UnallocatedTask>) unallocatedTaskRepository.findAll();
    }

    public void removeAll(List<UnallocatedTask> toRemove) {
        unallocatedTaskRepository.deleteAll(toRemove);
    }

    public void addAll(List<Long> banksIds, LocalDate today) {
        List<Bank> banks = (List<Bank>) bankRepository.findAllById(banksIds);
        List<UnallocatedTask> unallocatedTaskList = banks.stream()
                .map(b -> new UnallocatedTask()
                        .setBank(b)
                        .setDesiredDate(today))
                .toList();
        unallocatedTaskRepository.saveAll(unallocatedTaskList);
    }
}
