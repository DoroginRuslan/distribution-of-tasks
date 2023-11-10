package ru.era.distributionoftasks.services.distributor.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class TasksAgencyPoints {
    private List<AgencyPoint> agencyPointList;
    @Getter
    private List<AgencyPoint> agencyPointListHighPriority = new ArrayList<>();
    @Getter
    private List<AgencyPoint> agencyPointListMediumPriority = new ArrayList<>();
    @Getter
    private List<AgencyPoint> agencyPointListLowPriority = new ArrayList<>();

    public TasksAgencyPoints(List<AgencyPoint> agencyPointList) {
        this.agencyPointList = agencyPointList;
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

    //todo перепроверить по правильности условия
    private boolean getTasksWithHighPriority(AgencyPoint agencyPoint) {
        if (agencyPoint.getNumberOfDaysOfIssue() > 14) {
            return true;
        } else if (agencyPoint.getNumberOfDaysOfIssue() > 7 && agencyPoint.getNumberOfApproved() > 0) {
            return true;
        }
        return false;
    }

    private boolean getTasksWithMediumPriority(AgencyPoint agencyPoint) {
        if (agencyPoint.getNumberOfIssued() > 0 && (agencyPoint.getNumberOfIssued() / agencyPoint.getNumberOfApproved()) * 100 < 50) {
            return true;
        }
        return false;
    }

    private boolean getTasksWithLowPriority(AgencyPoint agencyPoint) {
        if (agencyPoint.getPointsConnected().equals("вчера") || !agencyPoint.isDelivered()) {
            return true;
        }
        return false;
    }

}
