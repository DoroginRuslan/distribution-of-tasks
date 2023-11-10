package ru.era.distributionoftasks.services.distributor;


import ru.era.distributionoftasks.services.distributor.entity.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.era.distributionoftasks.services.distributor.entity.Task.*;

public class BuildingRoutes {
    public static List<IntPointPair> buildingRouteFromOfficeToPointToPoint(Office office, AddressTimesMatrix addressTimesMatrix, int[] priorityTask, int desiredRemainingTime, TasksAgencyPoints tasksAgencyPoints) {
        List<AgencyPoint> firstTypePriorityPoints = switch (priorityTask[0]) {
            case TIME_HIGH_TASK -> tasksAgencyPoints.getAgencyPointListHighPriority();
            case TIME_MEDIUM_TASK -> tasksAgencyPoints.getAgencyPointListMediumPriority();
            case TIME_LOW_TASK -> tasksAgencyPoints.getAgencyPointListLowPriority();
            default -> throw new IllegalStateException("Задачи такого типа нет: " + priorityTask[0]);
        };

        List<AgencyPoint> secondTypePriorityPoints = switch (priorityTask[1]) {
            case TIME_HIGH_TASK -> tasksAgencyPoints.getAgencyPointListHighPriority();
            case TIME_MEDIUM_TASK -> tasksAgencyPoints.getAgencyPointListMediumPriority();
            case TIME_LOW_TASK -> tasksAgencyPoints.getAgencyPointListLowPriority();
            default -> throw new IllegalStateException("Задачи такого типа нет: " + priorityTask[1]);
        };

        List<IntPointPair> listRoute = new ArrayList<>();
        int maxRestDis = 0;

        for(AgencyPoint firstTypePoint : firstTypePriorityPoints) {
            int distanceFromOfficeToFirstPoint = addressTimesMatrix.getTimeBetweenAddresses(office.getAddressId(), firstTypePoint.getAddressId());
            for(AgencyPoint secondTypePoint : secondTypePriorityPoints) {
                int pointsDistance = addressTimesMatrix.getTimeBetweenAddresses(firstTypePoint.getAddressId(), secondTypePoint.getAddressId());
                int restEmployeeTime = TIME_MAX_TASK - (distanceFromOfficeToFirstPoint + priorityTask[0] + pointsDistance + priorityTask[1]);
            }
        }
        for (int i = 0; i < firstTypePriorityPoints.size(); i++) {
            for (int j = 0; j < secondTypePriorityPoints.size(); j++) {
                int point1;
                int point2;
                int disPoint1ToPoint2;

                point1 = firstTypePriorityPoints.get(i).getDatabaseId();
                point2 = secondTypePriorityPoints.get(j).getDatabaseId();
                disPoint1ToPoint2 = addressTimesMatrix[i][j];

                if (point1 != point2 && i != j) {
                    int restDis = TIME_MAX_TASK - (office[i] + priorityTask[0] + disPoint1ToPoint2 + priorityTask[1]);
                    if (restDis >= desiredRemainingTime && restDis > maxRestDis) {
                        maxRestDis = restDis;

                        List<Integer> list = new ArrayList<>();
                        list.add(restDis);
                        list.add(point1);
                        list.add(point2);

                        listRoute.add(list);
                    }
                }
            }
        }

        listRoute = sortedList(listRoute);

        return listRoute;
    }

    public static List<List<Integer>> buildingRouteFromPointToPoint(List<IntPointPair> officeRoutes, int[][] disPointToPoint, int priorityTask, TasksAgencyPoints tasksAgencyPoints) {
        List<AgencyPoint> listAgencyPoints1 = switch (priorityTask) {
            case TIME_HIGH_TASK -> tasksAgencyPoints.getAgencyPointListHighPriority();
            case TIME_MEDIUM_TASK -> tasksAgencyPoints.getAgencyPointListMediumPriority();
            case TIME_LOW_TASK -> tasksAgencyPoints.getAgencyPointListLowPriority();
            default -> throw new IllegalStateException("Задачи такого типа нет: " + priorityTask);
        };

        for (int i = 0; i < officeRoutes.size(); i++) {
            int maxDis = 0;
            int dis = officeRoutes.get(i).getLastTime(); // оставшееся время у сотрудника в минутах

           /* int sizeRoute = officeRoutes.get(i).getAgencyPointList().size();
            int[] pointsRoute = new int[sizeRoute]; //текущий маршрут


            for (int j = 0; j < pointsRoute.length; j++) {
                pointsRoute[j] = officeRoutes.get(i).getAgencyPointList().get(j); //получение точек маршрута
            }*/

        //    officeRoutes.get(i).add(-1); // маршрут до следующей точки равен бесконечности

            int index = listAgencyPoints1.indexOf(pointsRoute[sizeRoute - 1]);

            for (int j = 0; j < disPointToPoint.length; j++) {
                if (pointsRoute[sizeRoute - 1] != -1 && index != -1) {
                    if (listAgencyPoints1.get(j).getDatabaseId() == pointsRoute[1] || listAgencyPoints1.get(j).getDatabaseId() == pointsRoute[2] || listAgencyPoints1.get(j).getDatabaseId() == pointsRoute[sizeRoute - 1]) {
                        continue;
                    }

                    int restDis = dis - (disPointToPoint[index][j] + priorityTask); //остаток рабочего времени

                    if (restDis >= maxDis) {
                        maxDis = restDis;
                        officeRoutes.get(i).set(0, restDis); // обновили остаток времени
                        officeRoutes.get(i).set(sizeRoute, listAgencyPoints1.get(j).getDatabaseId()); // в конец массива добавили точку
                    }
                }
            }
        }

        removeEmptyRoutes(officeRoutes);
        officeRoutes = sortedList(officeRoutes);

        return officeRoutes;
    }

//    public static void findDuplicateRoutes(List<List<AgencyPoint>> listRoutesOffice1, List<List<AgencyPoint>> listRoutesOffice2) {
public static void findDuplicateRoutes(List<IntPointPair> listRoutesOffice1, List<IntPointPair> listRoutesOffice2) {
        List<IntPointPair> maxListRoutes = listRoutesOffice1.size() - listRoutesOffice2.size() > 0 ? listRoutesOffice1 : listRoutesOffice2;
        List<IntPointPair> minListRoutes = listRoutesOffice1.size() - listRoutesOffice2.size() < 0 ? listRoutesOffice1 : listRoutesOffice2;
        for(IntPointPair intPointFromMax : maxListRoutes) {
            for(AgencyPoint agencyPointFromMax : intPointFromMax.getAgencyPointList()) {
                for(IntPointPair intPointFromMin : minListRoutes) {
                    for(AgencyPoint agencyPointFromMin : intPointFromMin.getAgencyPointList()) {
                        if(agencyPointFromMax.getDatabaseId() == agencyPointFromMin.getDatabaseId()) {
                            if(intPointFromMax.getLastTime() > intPointFromMin.getLastTime()) {
                                intPointFromMin.setLastTime(-1);
                            } else if(intPointFromMax.getLastTime() < intPointFromMin.getLastTime()) {
                                intPointFromMax.setLastTime(-1);
                            } else {
                                int activeRoutesByMax = getAmmRoutesForOffice(maxListRoutes);
                                int activeRoutesByMin = getAmmRoutesForOffice(minListRoutes);
                                if(activeRoutesByMax <= activeRoutesByMin) {
                                    intPointFromMin.setLastTime(-1);
                                } else {
                                    intPointFromMax.setLastTime(-1);
                                }
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

    private static List<List<Integer>> sortedList(List<List<Integer>> listRoute) {
        listRoute = listRoute.stream().sorted((o1, o2) -> {
            for (int i = 0; i < Math.max(o1.size(), o2.size()); i++) {
                int c = o2.get(i).compareTo(o1.get(i));
                if (c != 0) {
                    return c;
                }
            }
            return Integer.compare(o1.size(), o2.size());
        }).collect(Collectors.toList());

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
