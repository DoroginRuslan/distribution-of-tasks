package ru.era.distributionoftasks.services.distributor;

import ru.era.distributionoftasks.services.distributor.entity.AlgEmployee;
import ru.era.distributionoftasks.services.distributor.entity.EmployeeRoute;
import ru.era.distributionoftasks.services.distributor.entity.Route;

import java.util.*;
import java.util.Map.Entry;

public class RoutesAnalyser {

//    public static void removeDuplicate2(List<Office> officeList) {
    private Map<AlgEmployee, Route> resultRoutes;
    private Map<AlgEmployee, Integer> flowIds;
    private Map<AlgEmployee, List<Route>> employeeRoutesMap;
    private Map<AlgEmployee, Integer> linksMap;
    private Set<AlgEmployee> moveDownSet;

    public Map<AlgEmployee, Route> removeDuplicate(List<EmployeeRoute> employeeRouteList) {
        resultRoutes = new HashMap<>();
        flowIds = new HashMap<>();
        employeeRoutesMap = new HashMap<>();
        for(EmployeeRoute employeeRoute : employeeRouteList) {
            employeeRoutesMap.put(employeeRoute.getAlgEmployee(), employeeRoute.getRoutes());
            resultRoutes.put(employeeRoute.getAlgEmployee(), null);
            flowIds.put(employeeRoute.getAlgEmployee(), 0);
        }
        List<AlgEmployee> notFilledEmployees = employeeRoutesMap.keySet().stream().toList();

        while (!checkResultRoutesFill(resultRoutes)) {
            moveDownSet = new HashSet<>();
            // проверка на конфликты с фиксированными маршрутами
            for(AlgEmployee employee : notFilledEmployees) {
                Route checkingRoute = employeeRoutesMap.get(employee).get(flowIds.get(employee));
                if(isConflictWithFixed(resultRoutes.values(), checkingRoute)) {
                    moveDownSet.add(employee);
                }
            }
            // проверка на кол-во конфликтов
            List<Entry<AlgEmployee, Integer>> conflicts;
            do {
                linksMap = new HashMap<>();
                for(AlgEmployee employee : notFilledEmployees) {
                    if(!moveDownSet.contains(employee)) {
                        linksMap.put(employee, 0);
                    }
                }
                List<AlgEmployee> linksKeys = linksMap.keySet().stream().toList();
                for(int i = 0; i < linksKeys.size(); i++) {
                    AlgEmployee employee1 = linksKeys.get(i);
                    Route route1 = getFlowRouteByEmployee(employee1);
                    for(int j = i+1; j < linksKeys.size(); j++) {
                        AlgEmployee employee2 = linksKeys.get(j);
                        Route route2 = getFlowRouteByEmployee(employee2);
                        if(route1.isConflict(route2)) {
                            linksMap.put(employee1, linksMap.get(employee1) + 1);
                            linksMap.put(employee2, linksMap.get(employee2) + 1);
                        }
                    }
                }
                conflicts = linksMap.entrySet().stream().sorted((e1,e2) -> e2.getValue() - e1.getValue()).toList();
                if(!conflicts.isEmpty()) {
                    moveDownSet.add(conflicts.get(0).getKey());
                }
            } while (!conflicts.isEmpty() && conflicts.get(0).getValue() != 0);
            linksMap.keySet().forEach(e -> resultRoutes.put(e, getFlowRouteByEmployee(e)));
            moveDownSet.forEach(e -> flowIds.put(e, flowIds.get(e) + 1));
        }
        return resultRoutes;
    }

    private Route getFlowRouteByEmployee(AlgEmployee employee) {
        return employeeRoutesMap.get(employee).get(linksMap.get(employee));
    }

    private boolean checkResultRoutesFill(Map<AlgEmployee, Route> map) {
        for(AlgEmployee employee : map.keySet()) {
            if(map.get(employee) == null) {
                return false;
            }
        }
        return true;
    }

    private boolean isConflictWithFixed(Collection<Route> fixedRoutes, Route checkingRoute) {
        for(Route route : fixedRoutes) {
            if(route != null && route.isConflict(checkingRoute)) {
                return true;
            }
        }
        return false;
    }
}
