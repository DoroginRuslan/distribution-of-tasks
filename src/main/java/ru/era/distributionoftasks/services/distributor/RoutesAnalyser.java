package ru.era.distributionoftasks.services.distributor;

import ru.era.distributionoftasks.entities.Employee;
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
    private List<EmployeeRoute> employeeRouteList;

    void getRoutes(List<EmployeeRoute> employeeRouteList, int flowId, List<Route> tail, int board) {
        if(flowId == employeeRouteList.size()) {
            System.out.println(tail);
//            for(Route route : tail) {
//                for(var a : route.getAgencyPointList()) {
//                    System.out.print(a.getAddressId() + " ");
//                }
//                System.out.print("\t");
//            }
//            System.out.println("");
//            if(checkScheme(tail)) {
//                System.out.println(tail);
//            }
//            return List.of(tail);
        } else {
            for(int i = 0; i < employeeRouteList.get(flowId).getRoutes().size(); i++) {
                Route route = employeeRouteList.get(flowId).getRoutes().get(i);
                if(checkScheme(tail, route)) {
                    tail.add(route);
                    getRoutes(employeeRouteList, flowId+1, tail, board);
                    if(flowId != 0) {
                        return;
                    } else {
                        tail.clear();
                    }
                }
//                tail.add(employeeRouteList.get(flowId).getRoutes().get(i));
//                if(checkScheme(tail)) {
//                    getRoutes(employeeRouteList, flowId+1, list, board);
//                }
            }
            getRoutes(employeeRouteList, flowId+1, tail, board);
//            System.out.println("Уникальный маршрут не найден");
        }
    }

    boolean checkScheme(List<Route> routes, Route route) {
        for(Route r1 : routes) {
            if(r1.isConflict(route)) {
                return false;
            }
        }
        return true;

//        for(int i = 0; i < routes.size()-1; i++) {
//            for(int j = i+1; j < routes.size(); j++) {
//                if(routes.get(i).isConflict(routes.get(j))) {
//                    return false;
//                }
//            }
//        }
//        return true;
    }
    public Map<AlgEmployee, Route> removeDuplicate(List<EmployeeRoute> employeeRouteList) {
        List<EmployeeRoute> sorted = employeeRouteList.stream().sorted(Comparator.comparingInt(o -> o.getRoutes().size())).toList();
        getRoutes(sorted, 0, new ArrayList<>(), 10);

//        resultRoutes = new HashMap<>();
//        flowIds = new HashMap<>();
//        employeeRoutesMap = new HashMap<>();
//        this.employeeRouteList = employeeRouteList;
//        for(EmployeeRoute employeeRoute : employeeRouteList) {
//            employeeRoutesMap.put(employeeRoute.getAlgEmployee(), employeeRoute.getRoutes());
//            resultRoutes.put(employeeRoute.getAlgEmployee(), null);
//            flowIds.put(employeeRoute.getAlgEmployee(), 0);
//        }
//
//        while (!checkResultRoutesFill(resultRoutes)) {
//            moveDownSet = new HashSet<>();
//            for(Employee employee : notFilledEmployees())
//            // проверка на конфликты с фиксированными маршрутами
//            for(AlgEmployee employee : notFilledEmployees()) {
//                Route checkingRoute = null;
//                try {
//                    checkingRoute = employeeRoutesMap.get(employee).get(flowIds.get(employee));
//                } catch(Exception e) {
//                    System.out.println("");
//                }
//                if(isConflictWithFixed(resultRoutes.values(), checkingRoute)) {
//                    moveDownSet.add(employee);
//                }
//            }
//            // проверка на кол-во конфликтов
//            List<Entry<AlgEmployee, Integer>> conflicts;
//            do {
//                linksMap = new HashMap<>();
//                for(AlgEmployee employee : notFilledEmployees()) {
//                    if(!moveDownSet.contains(employee)) {
//                        linksMap.put(employee, 0);
//                    }
//                }
//                List<AlgEmployee> linksKeys = linksMap.keySet().stream().toList();
//                for(int i = 0; i < linksKeys.size()-1; i++) {
//                    AlgEmployee employee1 = linksKeys.get(i);
//                    Route route1 = getFlowRouteByEmployee(employee1);
//                    for(int j = i+1; j < linksKeys.size(); j++) {
//                        AlgEmployee employee2 = linksKeys.get(j);
//                        Route route2 = getFlowRouteByEmployee(employee2);
//                        if(route1.isConflict(route2)) {
//                            linksMap.put(employee1, linksMap.get(employee1) + 1);
//                            linksMap.put(employee2, linksMap.get(employee2) + 1);
//                        }
//                    }
//                }
//                conflicts = linksMap.entrySet().stream().sorted((e1,e2) -> e2.getValue() - e1.getValue()).toList();
//                if(!conflicts.isEmpty() && conflicts.get(0).getValue() != 0) {
//                    moveDownSet.add(conflicts.get(0).getKey());
//                }
//            } while (!conflicts.isEmpty() && conflicts.get(0).getValue() != 0);
//            for (AlgEmployee employee : linksMap.keySet()) {
//                resultRoutes.put(employee, getFlowRouteByEmployee(employee));
//            }
//            moveDownSet.forEach(e -> flowIds.put(e, flowIds.get(e) + 1));
//            moveDownSet.clear();
//        }
//        return resultRoutes;
        return new HashMap<>();
    }

    private Route getFlowRouteByEmployee(AlgEmployee employee) {
        return employeeRoutesMap.get(employee).get(flowIds.get(employee));
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

    private List<AlgEmployee> notFilledEmployees() {
        List<AlgEmployee> res = new ArrayList<>();
        for(EmployeeRoute employeeRoute : employeeRouteList) {
            if(resultRoutes.get(employeeRoute.getAlgEmployee()) == null) {
                res.add(employeeRoute.getAlgEmployee());
            }
        }
        return res;
    }
}
