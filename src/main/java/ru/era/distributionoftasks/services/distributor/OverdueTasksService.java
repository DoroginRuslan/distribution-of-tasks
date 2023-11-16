package ru.era.distributionoftasks.services.distributor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.entities.Bank;
import ru.era.distributionoftasks.entities.UnallocatedTask;
import ru.era.distributionoftasks.services.UnallocatedTaskService;
import ru.era.distributionoftasks.services.TaskLogService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OverdueTasksService {
    @Autowired
    private TaskLogService taskLogService;
    @Autowired
    private UnallocatedTaskService unallocatedTaskService;

    public Map<Bank, Integer> getOverdueTasks(LocalDate today) {
        Map<Bank, Integer> result = new HashMap<>();
        Map<Bank, Integer> unallocatedTasksOverdueDaysMap = unallocatedTaskService.getUnallocatedTasksWithOverdue(today);
        Map<Bank, Integer> outstandingTasksOverdueDaysMap = taskLogService.getOverdueTasksWithOverdue(today);
        result.putAll(unallocatedTasksOverdueDaysMap);
        result.putAll(outstandingTasksOverdueDaysMap);
        return result;
    }

    public void updateOverdueTasks(List<Long> nonDistributeBanks, List<Long> distributeBanks, LocalDate today) {
        List<UnallocatedTask> flowUnallocatedTasks = unallocatedTaskService.findAll();
        List<UnallocatedTask> toRemove = new ArrayList<>();
        List<Long> toAdd = new ArrayList<>();
        for(Long distributeBank : distributeBanks) {
            UnallocatedTask unallocatedTask = contains(distributeBank, flowUnallocatedTasks);
            if(unallocatedTask != null) {
                toRemove.add(unallocatedTask);
            }
        }
        for(Long nonDistributeBank : nonDistributeBanks) {
            UnallocatedTask unallocatedTask = contains(nonDistributeBank, flowUnallocatedTasks);
            if(unallocatedTask == null) {
                toAdd.add(nonDistributeBank);
            }
        }
        unallocatedTaskService.removeAll(toRemove);
        unallocatedTaskService.addAll(toAdd, today);
    }

    private UnallocatedTask contains(Long bankId, List<UnallocatedTask> unallocatedTaskList) {
        for(UnallocatedTask flow : unallocatedTaskList) {
            if(flow.getBank().getId() == bankId) {
                return flow;
            }
        }
        return null;
    }
}
