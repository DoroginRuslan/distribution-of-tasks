package ru.era.distributionoftasks.services.distributor;


import ru.era.distributionoftasks.services.distributor.entity.*;

import java.util.*;


public class ServiceTaskAssignment {
    private final AddressTimesMatrix addressTimesMatrix;
    private final List<AgencyPoint> agencyPointList;
    private final List<Office> officeList;

    public ServiceTaskAssignment(AddressTimesMatrix addressTimesMatrix, List<AgencyPoint> agencyPointList, List<Office> officeList) {
        this.addressTimesMatrix = addressTimesMatrix;
        this.agencyPointList = agencyPointList;
        this.officeList = officeList;
    }

    // Основной метод для получения маршрутов
    public List<Office> calcEmployeeRoutes() {
        for(AgencyPoint agencyPoint : agencyPointList) {
            agencyPoint.setTask(TaskAnalyser.calcTaskForAgencyPoint(agencyPoint).orElse(null));
        }
        for(Office office : officeList) {
            for(AlgEmployee algEmployee : office.getEmployees()) {
                List<Route> employeeRoutes = calcRoutesForEmployee(algEmployee, office).stream().sorted().toList();
                office.getEmployeeRoutesVariantsMap().put(algEmployee, employeeRoutes);
            }
        }

        return officeList;
    }

    private List<Route> calcRoutesForEmployee(AlgEmployee algEmployee, Office office) {
        return calcRoutes(new Route(office.getAddressId(), addressTimesMatrix), AlgEmployee.WORK_DAY_IN_MINUTES,algEmployee.getRang());
    }

    private List<Route> calcRoutes(Route flowRoute, int maxRouteTime, Rang rang) {
        List<Route> result = new ArrayList<>();
        if(flowRoute.isEmpty()) {
            for(AgencyPoint agencyPoint : agencyPointList) {
                if(!checkPointAvailable(agencyPoint, rang)) continue;
                if(flowRoute.getTimeWithNewPoint(agencyPoint) <= maxRouteTime) {
                    Route route = new Route(flowRoute);
                    route.addAgencyPointToEnd(agencyPoint);
                    result.addAll(calcRoutes(route, maxRouteTime, rang));
                }
            }
        } else {
            for(AgencyPoint agencyPoint : agencyPointList) {
                if (!checkPointAvailable(agencyPoint, rang)) continue;
                if(!flowRoute.contains(agencyPoint)) {
                    if(flowRoute.getTimeWithNewPoint(agencyPoint) <= maxRouteTime) {
                        Route route = new Route(flowRoute);
                        route.addAgencyPointToEnd(agencyPoint);
                        result.addAll(calcRoutes(route, maxRouteTime, rang));
                    }
                }
            }
            if(result.isEmpty()) {
                flowRoute.updateProfit();
                result.add(flowRoute);
            }
        }
        return result;
    }

    private boolean checkPointAvailable(AgencyPoint agencyPoint, Rang rang) {
        return agencyPoint.getTask() != null &&
                agencyPoint.getTask().checkAvailableForRank(rang);
    }
}