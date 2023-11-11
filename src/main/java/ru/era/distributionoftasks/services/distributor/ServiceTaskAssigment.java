package ru.era.distributionoftasks.services.distributor;


import ru.era.distributionoftasks.services.distributor.entity.*;

import java.util.*;


public class ServiceTaskAssigment {
    private final AddressTimesMatrix addressTimesMatrix;
    private final List<AgencyPoint> agencyPointList;
    private final List<Office> officeList;

    public ServiceTaskAssigment(AddressTimesMatrix addressTimesMatrix, List<AgencyPoint> agencyPointList, List<Office> officeList) {
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
                office.getEmployeeRoutesVariantsMap().put(algEmployee, calcRoutesForEmployee(algEmployee, office));
            }
        }

        return officeList;
    }

    private List<Route> calcRoutesForEmployee(AlgEmployee algEmployee, Office office) {
        return calcRoutes(new Route(office.getAddressId(), addressTimesMatrix), AlgEmployee.WORK_DAY_IN_MINUTES);
    }

    private List<Route> calcRoutes(Route flowRoute, int maxRouteTime) {
        List<Route> result = new ArrayList<>();
        if(flowRoute.isEmpty()) {
            for(AgencyPoint agencyPoint : agencyPointList) {
                if(agencyPoint.getTask() == null) continue;
                if(flowRoute.getTimeWithNewPoint(agencyPoint) <= maxRouteTime) {
                    Route route = new Route(flowRoute);
                    route.addAgencyPointToEnd(agencyPoint);
                    result.addAll(calcRoutes(route, maxRouteTime));
                }
            }
        } else {
            for(AgencyPoint agencyPoint : agencyPointList) {
                if (agencyPoint.getTask() == null) continue;
                if(!flowRoute.contains(agencyPoint)) {
                    if(flowRoute.getTimeWithNewPoint(agencyPoint) <= maxRouteTime) {
                        Route route = new Route(flowRoute);
                        route.addAgencyPointToEnd(agencyPoint);
                        result.addAll(calcRoutes(route, maxRouteTime));
                    }
                }
            }
            if(result.isEmpty()) {
                result.add(flowRoute);
            }
        }
        return result;
    }
}