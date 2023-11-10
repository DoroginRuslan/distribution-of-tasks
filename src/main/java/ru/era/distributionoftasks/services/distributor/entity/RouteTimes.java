package ru.era.distributionoftasks.services.distributor.entity;

import java.util.List;

public class RouteTimes {
    //todo найти минимальное и максимальное время между агентскими точками
    public static final int[] TIME_ROUTE = {1, 50}; //время на дорогу минимальное и максимальное
    private int[][] disOfficeToLowTask, disOfficeToMediumTask, disOfficeToHighTask; // первая ячейка номер офиса
    private int[][] disMediumToMediumTask, disLowToLowTask, disLowToMediumTask, disLowToHighTask, disMediumToHighTask, disMediumToLowTask,
            disHighToMediumTask, disHighToLowTask;

    private List<AgencyPoint> pointsWithTaskLowPriority, pointsWithTaskMediumPriority, pointsWithTaskHighPriority;

    private List<Office> pointsOffice;
    private final AddressTimesMatrix addressTimesMatrix;

    public RouteTimes(List<Office> pointsOffice, List<AgencyPoint> pointsWithTaskLowPriority, List<AgencyPoint> pointsWithTaskMediumPriority, List<AgencyPoint> pointsWithTaskHighPriority, AddressTimesMatrix addressTimesMatrix) {
        this.pointsOffice = pointsOffice;
        this.pointsWithTaskLowPriority = pointsWithTaskLowPriority;
        this.pointsWithTaskMediumPriority = pointsWithTaskMediumPriority;
        this.pointsWithTaskHighPriority = pointsWithTaskHighPriority;
        this.addressTimesMatrix = addressTimesMatrix;
        init();
    }

    private void init() {
        int sizeOffice = pointsOffice.size();
        int sizeTasksHighPriority = pointsWithTaskHighPriority.size();
        int sizeTasksMediumPriority = pointsWithTaskMediumPriority.size();
        int sizeTasksLowPriority = pointsWithTaskLowPriority.size();

        if (sizeTasksLowPriority > 0) {
            disOfficeToLowTask = new int[sizeOffice][sizeTasksLowPriority];
            for (int i = 0; i < sizeOffice; i++) {
                getRouteTimesOfficeToPointFromDB(pointsOffice.get(i), pointsWithTaskLowPriority, disOfficeToLowTask[i]);
            }

            disLowToLowTask = new int[sizeTasksLowPriority][sizeTasksLowPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskLowPriority, pointsWithTaskLowPriority, disLowToLowTask);
        }

        if (sizeTasksMediumPriority > 0) {
            disOfficeToMediumTask = new int[sizeOffice][sizeTasksMediumPriority];
            for (int i = 0; i < sizeOffice; i++) {
                getRouteTimesOfficeToPointFromDB(pointsOffice.get(i), pointsWithTaskLowPriority, disOfficeToMediumTask[i]);
            }

            disMediumToMediumTask = new int[sizeTasksMediumPriority][sizeTasksMediumPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskMediumPriority, pointsWithTaskMediumPriority, disMediumToMediumTask);
        }

        if (sizeTasksHighPriority > 0) {
            disOfficeToHighTask = new int[sizeOffice][sizeTasksHighPriority];
            for (int i = 0; i < sizeOffice; i++) {
                getRouteTimesOfficeToPointFromDB(pointsOffice.get(i), pointsWithTaskHighPriority, disOfficeToHighTask[i]);
            }
        }

        if (sizeTasksLowPriority > 0 && sizeTasksMediumPriority > 0) {
            disLowToMediumTask = new int[sizeTasksLowPriority][sizeTasksMediumPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskLowPriority, pointsWithTaskMediumPriority, disLowToMediumTask);
            disMediumToLowTask = new int[sizeTasksMediumPriority][sizeTasksLowPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskMediumPriority, pointsWithTaskLowPriority, disMediumToLowTask);
        }

        if (sizeTasksLowPriority > 0 && sizeTasksHighPriority > 0) {
            disLowToHighTask = new int[sizeTasksLowPriority][sizeTasksHighPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskLowPriority, pointsWithTaskHighPriority, disLowToHighTask);
            disHighToLowTask = new int[sizeTasksHighPriority][sizeTasksLowPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskHighPriority, pointsWithTaskLowPriority, disHighToLowTask);
        }

        if (sizeTasksMediumPriority > 0 && sizeTasksHighPriority > 0) {
            disMediumToHighTask = new int[sizeTasksMediumPriority][sizeTasksHighPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskMediumPriority, pointsWithTaskHighPriority, disMediumToHighTask);
            disHighToMediumTask = new int[sizeTasksHighPriority][sizeTasksMediumPriority];
            getRouteTimesPointToPointFromDB(pointsWithTaskHighPriority, pointsWithTaskMediumPriority, disHighToMediumTask);
        }
    }

    private void getRouteTimesOfficeToPointFromDB(Office office, List<AgencyPoint> points1, int[] disOfficeToPoints) {
        for (int i = 0; i < points1.size(); i++) {
            disOfficeToPoints[i] = addressTimesMatrix.getTimeBetweenAddresses(office.getAddressId(), points1.get(i).getAddressId());
        }
    }

    private void getRouteTimesPointToPointFromDB(List<AgencyPoint> points1, List<AgencyPoint> points2, int[][] disPointToPoint) {
        for (int i = 0; i < points1.size(); i++) {
            for (int j = 0; j < points2.size(); j++) {
                if (points1.get(i).getAddressId() == points2.get(j).getAddressId()) {
                    disPointToPoint[i][j] = 0;
                } else {
                    disPointToPoint[i][j] = addressTimesMatrix.getTimeBetweenAddresses(points1.get(i).getAddressId(), points2.get(j).getAddressId());
                }
            }
        }
    }

    public int[][] getDisOfficeToLowTask() {
        return disOfficeToLowTask;
    }

    public int[][] getDisOfficeToMediumTask() {
        return disOfficeToMediumTask;
    }

    public int[][] getDisOfficeToHighTask() {
        return disOfficeToHighTask;
    }

    public int[][] getDisMediumToMediumTask() {
        return disMediumToMediumTask;
    }

    public int[][] getDisLowToLowTask() {
        return disLowToLowTask;
    }

    public int[][] getDisLowToMediumTask() {
        return disLowToMediumTask;
    }

    public int[][] getDisLowToHighTask() {
        return disLowToHighTask;
    }

    public int[][] getDisMediumToHighTask() {
        return disMediumToHighTask;
    }

    public int[][] getDisMediumToLowTask() {
        return disMediumToLowTask;
    }

    public int[][] getDisHighToMediumTask() {
        return disHighToMediumTask;
    }

    public int[][] getDisHighToLowTask() {
        return disHighToLowTask;
    }

    public List<AgencyPoint> getPointsWithTaskLowPriority() {
        return pointsWithTaskLowPriority;
    }

    public List<AgencyPoint> getPointsWithTaskMediumPriority() {
        return pointsWithTaskMediumPriority;
    }

    public List<AgencyPoint> getPointsWithTaskHighPriority() {
        return pointsWithTaskHighPriority;
    }

    public List<Office> getPointsOffice() {
        return pointsOffice;
    }
}
