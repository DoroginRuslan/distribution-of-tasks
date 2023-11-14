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
            }
            getRoutes(employeeRouteList, flowId+1, tail, board);
        }
    }

    boolean checkScheme(List<Route> routes, Route route) {
        for(Route r1 : routes) {
            if(r1.isConflict(route)) {
                return false;
            }
        }
        return true;
    }
    public Map<AlgEmployee, Route> removeDuplicate(List<EmployeeRoute> employeeRouteList) {
        List<EmployeeRoute> sorted = employeeRouteList.stream().sorted(Comparator.comparingInt(o -> o.getRoutes().size())).toList();
        getRoutes(sorted, 0, new ArrayList<>(), 10);
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
