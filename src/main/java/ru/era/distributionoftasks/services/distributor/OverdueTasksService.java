package ru.era.distributionoftasks.services.distributor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.entities.Bank;
import ru.era.distributionoftasks.services.UnallocatedTaskService;
import ru.era.distributionoftasks.services.TaskLogService;
import ru.era.distributionoftasks.services.distributor.entity.AgencyPoint;

import java.time.LocalDate;
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

    public void updateOverdueTasks(List<AgencyPoint> outstandingTasks, List<AgencyPoint> completedTasks) {

    }
}
