package ru.era.distributionoftasks.services.distributor;


import lombok.Getter;
import ru.era.distributionoftasks.services.distributor.entity.*;

import java.util.*;


public class ServiceTaskAssignment {
    private final AddressTimesMatrix addressTimesMatrix;
    private final List<AgencyPoint> agencyPointList;
    private final List<Office> officeList;
    @Getter
    private final List<AgencyPoint> distributeAgencyPoints;
    @Getter
    private final List<AlgEmployee> distributeAlgEmployees;

    public ServiceTaskAssignment(AddressTimesMatrix addressTimesMatrix, List<AgencyPoint> agencyPointList, List<Office> officeList) {
        this.addressTimesMatrix = addressTimesMatrix;
        this.agencyPointList = agencyPointList;
        this.officeList = officeList;
        distributeAgencyPoints = new ArrayList<>();
        distributeAlgEmployees = new ArrayList<>();
    }

    // Основной метод для получения маршрутов
    public List<EmployeeRoutePair> calcEmployeeRoutes() {
        for(AgencyPoint agencyPoint : agencyPointList) {
            agencyPoint.setTask(TaskAnalyser.calcTaskForAgencyPoint(agencyPoint).orElse(null));
        }
        List<EmployeeRoutePair> resultRoutes = new ArrayList<>();
        int ammEmployees = officeList.stream().mapToInt(o -> o.getEmployees().size()).sum();
        while (distributeAlgEmployees.size() < ammEmployees){
            List<EmployeeRoute> employeeRoutesVariants = new ArrayList<>();
            for (Office office : officeList) {
                for (AlgEmployee algEmployee : office.getEmployees()) {
                    if (distributeAlgEmployees.contains(algEmployee)) continue;
                    List<Route> employeeRoutes = calcRoutesForEmployee(algEmployee, office).stream().sorted().toList();
                    if (employeeRoutes.isEmpty()) {
                        resultRoutes.add(new EmployeeRoutePair(algEmployee, new Route(office.getAddressId(), addressTimesMatrix)));
                        distributeAlgEmployees.add(algEmployee);
                    } else {
                        employeeRoutesVariants.add(new EmployeeRoute(algEmployee, employeeRoutes));
                    }
                }
            }
            List<EmployeeRoutePair> iterationRoutes = calcNoConflictRoutes(employeeRoutesVariants);
            resultRoutes.addAll(iterationRoutes);
            for (EmployeeRoutePair employeeRoutePair : iterationRoutes) {
                distributeAlgEmployees.add(employeeRoutePair.getAlgEmployee());
                distributeAgencyPoints.addAll(employeeRoutePair.getRoute().getAgencyPointList());
            }
        }
        return resultRoutes;
    }

    private List<EmployeeRoutePair> calcNoConflictRoutes(List<EmployeeRoute> employeeRoutesVariants) {
        List<EmployeeRoutePair> result = new ArrayList<>();
        List<EmployeeRoutePair> discard = new ArrayList<>();
        List<EmployeeRoutePair> topRoutes;
        Map<EmployeeRoutePair, Integer> routeLinks;
        do {
            topRoutes = new ArrayList<>();
            routeLinks = new HashMap<>();
            for (EmployeeRoute employeeRoute : employeeRoutesVariants) {
                if(isEntries(employeeRoute, result) || isEntries(employeeRoute, discard)) continue;
                EmployeeRoutePair employeeRoutePair = new EmployeeRoutePair(employeeRoute.getAlgEmployee(), employeeRoute.getRoutes().get(0));
                topRoutes.add(employeeRoutePair);
                routeLinks.put(employeeRoutePair, 0);
            }
            calcLinks(topRoutes, routeLinks);
            topRoutes = topRoutes.stream()
//                    .sorted(Comparator.comparingInt(o -> o.getRoute().getProfit()))
                    .sorted(Comparator.comparingInt(routeLinks::get).reversed())
                    .toList();
            for(EmployeeRoutePair employeeRoutePair : topRoutes) {
                if(routeLinks.get(employeeRoutePair) == 0) {
                    result.add(employeeRoutePair);
                } else {
                    discard.add(employeeRoutePair);
                    break;
                }
            }
        } while (result.size() + discard.size() != employeeRoutesVariants.size());
        return result;
    }

    private static boolean isEntries(EmployeeRoute employeeRoute, List<EmployeeRoutePair> pairs) {
        long rCount = pairs.stream()
                .map(EmployeeRoutePair::getAlgEmployee)
                .filter(e -> e == employeeRoute.getAlgEmployee())
                .count();
        return rCount != 0;
    }

    private void calcLinks(List<EmployeeRoutePair> topRoutes, Map<EmployeeRoutePair, Integer> routeLinks) {
        for(int i = 0; i < topRoutes.size()-1; i++) {
            for(int j = i+1; j < topRoutes.size(); j++) {
                if(topRoutes.get(i).getRoute().isConflict(topRoutes.get(j).getRoute())) {
                    incValue(routeLinks, topRoutes.get(i));
                    incValue(routeLinks, topRoutes.get(j));
                }
            }
        }
    }

    private void incValue(Map<EmployeeRoutePair, Integer> map, EmployeeRoutePair key) {
        map.put(key, map.get(key)+1);
    }

    private List<Route> calcRoutesForEmployee(AlgEmployee algEmployee, Office office) {
        return calcRoutes(new Route(office.getAddressId(), addressTimesMatrix), AlgEmployee.WORK_DAY_IN_MINUTES, algEmployee.getRang());
    }

    private List<Route> calcRoutes(Route flowRoute, int maxRouteTime, Rang rang) {
        List<Route> result = new ArrayList<>();
        if(flowRoute.isEmpty()) {
            for(AgencyPoint agencyPoint : agencyPointList) {
                if(distributeAgencyPoints.contains(agencyPoint)) continue;
                if(!checkPointAvailable(agencyPoint, rang)) continue;
                if(flowRoute.getTimeWithNewPoint(agencyPoint) <= maxRouteTime) {
                    Route route = new Route(flowRoute);
                    route.addAgencyPointToEnd(agencyPoint);
                    result.addAll(calcRoutes(route, maxRouteTime, rang));
                }
            }
        } else {
            for(AgencyPoint agencyPoint : agencyPointList) {
                if(distributeAgencyPoints.contains(agencyPoint)) continue;
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
