package ru.era.distributionoftasks.services.distributor.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TasksAgencyPoints {
    private final List<AgencyPoint> agencyPointListHighPriority = new ArrayList<>();
    private final List<AgencyPoint> agencyPointListMediumPriority = new ArrayList<>();
    private final List<AgencyPoint> agencyPointListLowPriority = new ArrayList<>();

    public TasksAgencyPoints(List<AgencyPoint> agencyPointList) {
        getTasksWithPriority(agencyPointList);
    }


    private void getTasksWithPriority(List<AgencyPoint> agencyPoints) {
        for (int i = 0; i < agencyPoints.size(); i++) {
            AgencyPoint agencyPoint = agencyPoints.get(i);
            if (getTasksWithHighPriority(agencyPoint)) {
                agencyPointListHighPriority.add(agencyPoint);
                agencyPoint.setTaskPriority(Priority.MAX_PRIORITY);
            } else if (getTasksWithMediumPriority(agencyPoint)) {
                agencyPointListMediumPriority.add(agencyPoint);
                agencyPoint.setTaskPriority(Priority.MEDIUM_PRIORITY);
            } else if (getTasksWithLowPriority(agencyPoint)) {
                agencyPointListLowPriority.add(agencyPoint);
                agencyPoint.setTaskPriority(Priority.LOW_PRIORITY);
            }
        }

    }

}
