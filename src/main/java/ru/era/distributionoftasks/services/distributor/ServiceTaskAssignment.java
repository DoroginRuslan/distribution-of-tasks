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
    public Map<AlgEmployee, Route> calcEmployeeRoutes() {
        for(AgencyPoint agencyPoint : agencyPointList) {
            agencyPoint.setTask(TaskAnalyser.calcTaskForAgencyPoint(agencyPoint).orElse(null));
        }
        for(Office office : officeList) {
            for(AlgEmployee algEmployee : office.getEmployees()) {
                List<Route> employeeRoutes = calcRoutesForEmployee(algEmployee, office).stream().sorted().toList();
                office.getEmployeeRoutesVariantsMap().put(algEmployee, employeeRoutes);
            }
        }
        List<EmployeeRoute> employeeRouteList = new ArrayList<>();
        for(Office office : officeList) {
            for(var entries : office.getEmployeeRoutesVariantsMap().entrySet()) {
                employeeRouteList.add(new EmployeeRoute(entries.getKey(), entries.getValue()));
            }
        }
        List<EmployeeRoute> sorted = employeeRouteList.stream().sorted(Comparator.comparingInt(o -> o.getRoutes().size())).toList();
        List<AlgEmployee> employeesWithoutWork = new ArrayList<>();
        List<Route> routes = getRoutes(sorted, 0, new ArrayList<>(), employeesWithoutWork);
        System.out.println("Size = " + routes.size());
//        for(Route route : routes) {
//            for()
//        }
//        return new RoutesAnalyser().removeDuplicate(employeeRouteList);
//        return officeList;
        return null;
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

    private List<Route> getRoutes(List<EmployeeRoute> employeeRouteList, int flowId, List<Route> tail, List<AlgEmployee> employeesWithoutWork) {
        if(flowId == employeeRouteList.size()) {
            return tail;
        } else {
            for(int i = 0; i < employeeRouteList.get(flowId).getRoutes().size(); i++) {
                Route route = employeeRouteList.get(flowId).getRoutes().get(i);
                if(checkScheme(tail, route)) {
                    tail.add(route);
                    return getRoutes(employeeRouteList, flowId+1, tail, employeesWithoutWork);
                }
            }
        }
        employeesWithoutWork.add(employeeRouteList.get(flowId).getAlgEmployee());
        return getRoutes(employeeRouteList, flowId+1, tail, employeesWithoutWork);
    }

    private boolean checkPointAvailable(AgencyPoint agencyPoint, Rang rang) {
        return agencyPoint.getTask() != null &&
                agencyPoint.getTask().checkAvailableForRank(rang);
    }

    boolean checkScheme(List<Route> routes, Route route) {
        for(Route r1 : routes) {
            if(r1.isConflict(route)) {
                return false;
            }
        }
        return true;
    }
}