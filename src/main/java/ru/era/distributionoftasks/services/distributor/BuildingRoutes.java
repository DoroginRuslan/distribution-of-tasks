package ru.era.distributionoftasks.services.distributor;


import ru.era.distributionoftasks.services.distributor.entity.*;

import java.util.*;
import java.util.stream.Collectors;

import static ru.era.distributionoftasks.services.distributor.entity.Task.*;

public class BuildingRoutes {
    public static List<IntPointPair> buildingRouteFromOfficeToPointToPoint(Office office, AddressTimesMatrix addressTimesMatrix, int[] priorityTaskTime, int desiredRemainingTime, TasksAgencyPoints tasksAgencyPoints) {
        List<AgencyPoint> firstTypePriorityPoints = switch (priorityTaskTime[0]) {
            case TIME_HIGH_TASK -> tasksAgencyPoints.getAgencyPointListHighPriority();
            case TIME_MEDIUM_TASK -> tasksAgencyPoints.getAgencyPointListMediumPriority();
            case TIME_LOW_TASK -> tasksAgencyPoints.getAgencyPointListLowPriority();
            default -> throw new IllegalStateException("Задачи такого типа нет: " + priorityTaskTime[0]);
        };

        List<AgencyPoint> secondTypePriorityPoints = switch (priorityTaskTime[1]) {
            case TIME_HIGH_TASK -> tasksAgencyPoints.getAgencyPointListHighPriority();
            case TIME_MEDIUM_TASK -> tasksAgencyPoints.getAgencyPointListMediumPriority();
            case TIME_LOW_TASK -> tasksAgencyPoints.getAgencyPointListLowPriority();
            default -> throw new IllegalStateException("Задачи такого типа нет: " + priorityTaskTime[1]);
        };

        List<IntPointPair> listRoute = new ArrayList<>();
        int maxRestDis = 0;

        for(AgencyPoint firstTypePoint : firstTypePriorityPoints) {
            int distanceFromOfficeToFirstPoint = addressTimesMatrix.getTimeBetweenAddresses(office.getAddressId(), firstTypePoint.getAddressId());
            for(AgencyPoint secondTypePoint : secondTypePriorityPoints) {
                int pointsTimeDistance = addressTimesMatrix.getTimeBetweenAddresses(firstTypePoint.getAddressId(), secondTypePoint.getAddressId());
                if(firstTypePoint.getDatabaseId() != secondTypePoint.getDatabaseId()) {
                    int restEmployeeTime = TIME_MAX_TASK - (distanceFromOfficeToFirstPoint + priorityTaskTime[0] + pointsTimeDistance + priorityTaskTime[1]);
                    if (restEmployeeTime >= desiredRemainingTime/* && restEmployeeTime > maxRestDis*/) {
                        maxRestDis = restEmployeeTime;
                        List<AgencyPoint> agencyPointList = Arrays.asList(firstTypePoint, secondTypePoint);
                        IntPointPair route = new IntPointPair(restEmployeeTime, agencyPointList);
                        listRoute.add(route);
                    }
                }

            }
        }

        listRoute = sortedList(listRoute);

        return listRoute;
    }

    public static List<IntPointPair> buildingRouteFromPointToPoint(List<IntPointPair> officeRoutes, AddressTimesMatrix addressTimesMatrix, int priorityTaskTime, TasksAgencyPoints tasksAgencyPoints) {
        List<AgencyPoint> listAgencyPoints1 = switch (priorityTaskTime) {
            case TIME_HIGH_TASK -> tasksAgencyPoints.getAgencyPointListHighPriority();
            case TIME_MEDIUM_TASK -> tasksAgencyPoints.getAgencyPointListMediumPriority();
            case TIME_LOW_TASK -> tasksAgencyPoints.getAgencyPointListLowPriority();
            default -> throw new IllegalStateException("Задачи такого типа нет: " + priorityTaskTime);
        };

        List<IntPointPair> newOfficeRoutes = new ArrayList<>();
        for(IntPointPair route : officeRoutes) {
            AgencyPoint lastPoint = route.getAgencyPointList().get(route.getAgencyPointList().size()-1);
            int maxTimeDistance = 0;
            for(AgencyPoint agencyPoint : listAgencyPoints1) {
                if(route.getAgencyPointList().stream()
                        .map(AgencyPoint::getDatabaseId)
                        .toList().contains(agencyPoint.getDatabaseId())) {
                    int pointsTimeDistance = addressTimesMatrix.getTimeBetweenAddresses(lastPoint.getAddressId(), agencyPoint.getAddressId());
                    int restEmployeeTime = route.getLastTime() - (pointsTimeDistance + priorityTaskTime);
                    if(restEmployeeTime >= 0) {
                        maxTimeDistance = restEmployeeTime;
                        List<AgencyPoint> newRoute = new ArrayList<>(route.getAgencyPointList());
                        newRoute.add(agencyPoint);
                        newOfficeRoutes.add(new IntPointPair(restEmployeeTime, newRoute));
                    }
                }
            }
        }

        officeRoutes = sortedList(newOfficeRoutes);

        return officeRoutes;
    }

//    public static void findDuplicateRoutes(List<List<AgencyPoint>> listRoutesOffice1, List<List<AgencyPoint>> listRoutesOffice2) {
public static void findDuplicateRoutes(List<IntPointPair> listRoutesOffice1, List<IntPointPair> listRoutesOffice2, long countEmployeesOffice1, long countEmployeesOffice2) {
//        List<IntPointPair> maxListRoutes = listRoutesOffice1.size() - listRoutesOffice2.size() > 0 ? listRoutesOffice1 : listRoutesOffice2;
//        List<IntPointPair> minListRoutes = listRoutesOffice1.size() - listRoutesOffice2.size() < 0 ? listRoutesOffice1 : listRoutesOffice2;
        for(IntPointPair intPointFromOffice1 : listRoutesOffice1) {
            for(AgencyPoint agencyPointFromOffice1 : intPointFromOffice1.getAgencyPointList()) {
                for(IntPointPair intPointFromOffice2 : listRoutesOffice2) {
                    for(AgencyPoint agencyPointFromOffice2 : intPointFromOffice2.getAgencyPointList()) {
                        if(agencyPointFromOffice1.getDatabaseId() == agencyPointFromOffice2.getDatabaseId()) {
                            int activeRoutesByOffice1 = getAmmRoutesForOffice(listRoutesOffice1);
                            int activeRoutesByOffice2 = getAmmRoutesForOffice(listRoutesOffice2);
                            boolean office1Border = activeRoutesByOffice1 <= countEmployeesOffice1;
                            boolean office2Border = activeRoutesByOffice2 <= countEmployeesOffice2;
                            if(office1Border && office2Border || !office1Border && !office2Border) {
                                if(intPointFromOffice1.getLastTime() > intPointFromOffice2.getLastTime()) {
                                    intPointFromOffice2.setLastTime(-1);
                                } else if(intPointFromOffice1.getLastTime() < intPointFromOffice2.getLastTime()) {
                                    intPointFromOffice1.setLastTime(-1);
                                }
//                                else {
//                                    if(activeRoutesByOffice1 <= activeRoutesByOffice2) {
//                                        intPointFromOffice2.setLastTime(-1);
//                                    } else {
//                                        intPointFromOffice1.setLastTime(-1);
//                                    }
//                                }
                            } else if(office1Border) {
                                intPointFromOffice2.setLastTime(-1);
                            } else {
                                intPointFromOffice1.setLastTime(-1);
                            }

                        }
                    }
                }
            }
        }

        removeEmptyRoutes(listRoutesOffice1);
        removeEmptyRoutes(listRoutesOffice2);
    }

    private static int getAmmRoutesForOffice(List<IntPointPair> officeRoutes) {
        int delRoutes = 0;
        for(IntPointPair route : officeRoutes) {
            if(route.getLastTime() == -1) {
                delRoutes++;
            }
        }
        return officeRoutes.size()-delRoutes;
    }

    // TODO: 10.11.2023 Посмотреть в отладчике
    private static List<IntPointPair> sortedList(List<IntPointPair> listRoute) {
        listRoute = listRoute.stream().sorted(Comparator.comparingInt(IntPointPair::getLastTime)).collect(Collectors.toList());

        return listRoute;
    }

    private static List<IntPointPair> removeEmptyRoutes(List<IntPointPair> listRoute) {
        Iterator<IntPointPair> iterator = listRoute.iterator();
        while (iterator.hasNext()) {
            IntPointPair num = iterator.next();
            if (num.getLastTime() == -1) {
                iterator.remove();
            }
        }
        return listRoute;
    }
}
