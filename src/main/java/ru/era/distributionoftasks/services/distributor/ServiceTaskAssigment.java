package ru.era.distributionoftasks.services.distributor;


import ru.era.distributionoftasks.services.distributor.entity.*;

import java.util.*;

import static ru.era.distributionoftasks.services.distributor.BuildingRoutes.*;
import static ru.era.distributionoftasks.services.distributor.entity.RouteTimes.TIME_ROUTE;
import static ru.era.distributionoftasks.services.distributor.entity.Task.*;


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
    public List<EmployeeRoute> calcEmployeeRoutes() {
//        TasksAgencyPoints tasksAgencyPoints = new TasksAgencyPoints(agencyPointList);
        // Назначаем все варианты маршрутов для всех сотрудников
        for(Office office : officeList) {
            for(AlgEmployee algEmployee : office.getEmployees()) {
                office.getEmployeeRoutesVariantsMap().put(algEmployee, calcRoutesForEmployee(algEmployee, office));
            }
        }

        addRouteOffice(tasksAgencyPoints); // распределение маршрутов по офисам

        for(int i = 0; i < officeList.size()-1; i++) {
            for(int j = i+1; j < officeList.size(); j++) {
                findDuplicateRoutes(officeList.get(i).getListRoutesSignor(),
                        officeList.get(j).getListRoutesSignor(),
                        officeList.get(i).getCountEmployees(Rang.SENIOR_RANG),
                        officeList.get(j).getCountEmployees(Rang.SENIOR_RANG));
                findDuplicateRoutes(officeList.get(i).getListRoutesMiddle(),
                        officeList.get(j).getListRoutesMiddle(),
                        officeList.get(i).getCountEmployees(Rang.MIDDLE_RANG),
                        officeList.get(j).getCountEmployees(Rang.MIDDLE_RANG));
                findDuplicateRoutes(officeList.get(i).getListRoutesJunior(),
                        officeList.get(j).getListRoutesJunior(),
                        officeList.get(i).getCountEmployees(Rang.JUNIOR_RANG),
                        officeList.get(j).getCountEmployees(Rang.JUNIOR_RANG));
            }
        }

        List<EmployeeRoute> employeeRouteList = new ArrayList<>();
        for(Office office : officeList) {
            EmployeeRoute employeeRoute1 = null;
            try {
                employeeRoute1 = new EmployeeRoute(new AlgEmployee(1L, Rang.SENIOR_RANG), office.getListRoutesSignor().get(0).getAgencyPointList());
            } catch (Exception e) {
                e.printStackTrace();
            }
            EmployeeRoute employeeRoute2 = new EmployeeRoute(new AlgEmployee(1L, Rang.MIDDLE_RANG), office.getListRoutesMiddle().get(0).getAgencyPointList());
            EmployeeRoute employeeRoute3 = new EmployeeRoute(new AlgEmployee(1L, Rang.JUNIOR_RANG), office.getListRoutesMiddle().get(0).getAgencyPointList());
            employeeRouteList.add(employeeRoute1);
            employeeRouteList.add(employeeRoute2);
            employeeRouteList.add(employeeRoute3);
        }
        return employeeRouteList;
    }

    private List<Route> calcRoutesForEmployee(AlgEmployee employee, Office employeeOffice) {
        int startPoint = employeeOffice.getAddressId();
        for
        return null;
    }

    private List<Route> calcRouteRecursion(int startPoint, int lastTime, Route flowRoute)

    private void addRouteOffice(TasksAgencyPoints tasksAgencyPoints) {
        /****** Проверка на дублирование *****/
        for (Office office : officeList) {
            for (AlgEmployee algEmployee : office.getEmployeeList()) {
                List<Route> employeeRoutes;
                switch (algEmployee.getRang()){
                    case SENIOR_RANG ->  {
                        fixRoutesForSenior(office, tasksAgencyPoints);
                    }
                    case MIDDLE_RANG -> {
                        fixRoutesForMiddle(office, tasksAgencyPoints);
                    }
                    case JUNIOR_RANG -> {
                        fixRoutesForJune(office, tasksAgencyPoints);
                    }
                }
            }
            //todo добавить проверку есть ли вообще такой сотрудник в офисе и проверить, остались ли задачи нужного уровня

            /****** Проверка на дублирование *****/
            if (office.getListRoutesSignor() != null && office.getListRoutesMiddle() != null) {
                findDuplicateRoutes(office.getListRoutesSignor(), office.getListRoutesMiddle(), office.getCountEmployees(Rang.SENIOR_RANG), office.getCountEmployees(Rang.MIDDLE_RANG));
            }
            if (office.getListRoutesSignor() != null && office.getListRoutesJunior() != null) {
                findDuplicateRoutes(office.getListRoutesSignor(), office.getListRoutesJunior(), office.getCountEmployees(Rang.SENIOR_RANG), office.getCountEmployees(Rang.JUNIOR_RANG));
            }
            if (office.getListRoutesMiddle() != null && office.getListRoutesJunior() != null) {
                findDuplicateRoutes(office.getListRoutesMiddle(), office.getListRoutesJunior(), office.getCountEmployees(Rang.MIDDLE_RANG), office.getCountEmployees(Rang.JUNIOR_RANG));
            }
        }
    }

    private void fixRoutesForJune( Office office, TasksAgencyPoints tasksAgencyPoints) {
        List<Route> routesJunior = getRoutesFromOfficeToFourPoints(
                office,
                addressTimesMatrix,
                new int[]{TIME_LOW_TASK, TIME_LOW_TASK, TIME_LOW_TASK, TIME_LOW_TASK},
                tasksAgencyPoints);

        fixRoutesForMiddle(office, tasksAgencyPoints);
        if (routesJunior == null || routesJunior.isEmpty()) {
            routesJunior = getRoutesFromOfficeToThreePoints(office, addressTimesMatrix, new int[]{TIME_LOW_TASK, TIME_LOW_TASK, TIME_LOW_TASK}, tasksAgencyPoints);

            if (routesJunior == null || routesJunior.isEmpty()) {
                routesJunior = getRoutesFromOfficeToTwoPoints(office, addressTimesMatrix, new int[]{TIME_LOW_TASK, TIME_LOW_TASK}, tasksAgencyPoints);
            }
        }
        office.setListRoutesJunior(routesJunior);
    }

    private void fixRoutesForMiddle(Office office, TasksAgencyPoints tasksAgencyPoints) {
        List<Route> routesMiddle = getRoutesFromOfficeToThreePoints(
                office,
                addressTimesMatrix,
                new int[]{TIME_MEDIUM_TASK, TIME_MEDIUM_TASK, TIME_MEDIUM_TASK},
                tasksAgencyPoints);

        if (routesMiddle == null || routesMiddle.isEmpty()) {
            routesMiddle = getRoutesFromOfficeToThreePoints(office, addressTimesMatrix, new int[]{TIME_MEDIUM_TASK, TIME_MEDIUM_TASK, TIME_LOW_TASK}, tasksAgencyPoints);

            if (routesMiddle == null || routesMiddle.isEmpty()) {
                routesMiddle = getRoutesFromOfficeToThreePoints(office, addressTimesMatrix, new int[]{TIME_MEDIUM_TASK, TIME_LOW_TASK, TIME_MEDIUM_TASK}, tasksAgencyPoints);

                if (routesMiddle == null || routesMiddle.isEmpty()) {
                    routesMiddle = getRoutesFromOfficeToThreePoints(office, addressTimesMatrix, new int[]{TIME_LOW_TASK, TIME_MEDIUM_TASK, TIME_MEDIUM_TASK}, tasksAgencyPoints);

                    if (routesMiddle == null || routesMiddle.isEmpty()) {
                        routesMiddle = getRoutesFromOfficeToTwoPoints(office, addressTimesMatrix, new int[]{TIME_MEDIUM_TASK, TIME_LOW_TASK}, tasksAgencyPoints);
                    }
                }
            }
        }
        office.setListRoutesMiddle(routesMiddle);
    }

    private void fixRoutesForSenior(Office office, TasksAgencyPoints tasksAgencyPoints) {
        List<Route> routesSignor = getRoutesFromOfficeToThreePoints(
                office,
                addressTimesMatrix,
                new int[]{TIME_HIGH_TASK, TIME_MEDIUM_TASK, TIME_LOW_TASK},
                tasksAgencyPoints);
        if (routesSignor == null || routesSignor.isEmpty()) {
            routesSignor = getRoutesFromOfficeToTwoPoints(office, addressTimesMatrix, new int[]{TIME_MEDIUM_TASK, TIME_HIGH_TASK}, tasksAgencyPoints);

            if (routesSignor == null || routesSignor.isEmpty()) {
                routesSignor = getRoutesFromOfficeToTwoPoints(office, addressTimesMatrix, new int[]{TIME_HIGH_TASK, TIME_LOW_TASK}, tasksAgencyPoints);

                if (routesSignor == null || routesSignor.isEmpty()) {
                    routesSignor = getRoutesFromOfficeToTwoPoints(office, addressTimesMatrix, new int[]{TIME_LOW_TASK, TIME_HIGH_TASK}, tasksAgencyPoints);
                }
            }
        }
        office.setListRoutesSignor(routesSignor);
    }

    private static Map<int[], Integer> getDisFromOfficeToPoint(RouteTimes routeTimes, int priorityTask) {
        Map<int[], Integer> mapRoutes = new LinkedHashMap<>();
        switch (priorityTask) {
            case 1 -> {
                for (int i = 0; i < routeTimes.getDisOfficeToHighTask().length; i++) {
                 //   for (int j = 0; j < routeTimes.getDisOfficeToHighTask()[i].length; j++) {
                        mapRoutes.put(routeTimes.getDisOfficeToHighTask()[i], i);
                //    }
                }
            }
            case 2 -> {
                for (int i = 0; i < routeTimes.getDisOfficeToMediumTask().length; i++) {
                   /* for (int j = 0; j < routeTimes.getDisOfficeToMediumTask()[i].length; j++) {
                        mapRoutes.put(routeTimes.getDisOfficeToMediumTask()[j], routeTimes.getDisOfficeToMediumTask()[i][j]);
                    }*/
                    mapRoutes.put(routeTimes.getDisOfficeToMediumTask()[i], i);
                }
            }
            case 3 -> {
                for (int i = 0; i < routeTimes.getDisOfficeToLowTask().length; i++) {
                   /* for (int j = 0; j < routeTimes.getDisOfficeToLowTask()[i].length; j++) {
                        mapRoutes.put(routeTimes.getDisOfficeToLowTask()[j], routeTimes.getDisOfficeToLowTask()[i][j]);
                    }*/
                    mapRoutes.put(routeTimes.getDisOfficeToLowTask()[i],i);
                }
            }
            default -> throw new IllegalStateException("Задачи с таким приоритетом нет пока что: " + priorityTask);
        }
        return mapRoutes;
    }

    private static List<Route> getRoutesFromOfficeToTwoPoints(Office office,
                                                              AddressTimesMatrix addressTimesMatrix,
                                                              int[] timeTask,
                                                              TasksAgencyPoints tasksAgencyPoints) {
        return buildingRouteFromOfficeToPointToPoint(office, addressTimesMatrix,
                new int[]{timeTask[0], timeTask[1]}, 0, tasksAgencyPoints);
    }

    private static List<Route> getRoutesFromOfficeToThreePoints(Office office,
                                                                AddressTimesMatrix addressTimesMatrix,
                                                                int[] timeTask,
                                                                TasksAgencyPoints tasksAgencyPoints) {
        List<Route> routesFromOfficeToPointToPoint = buildingRouteFromOfficeToPointToPoint(office,
                addressTimesMatrix, new int[]{timeTask[0], timeTask[1]}, timeTask[0] + TIME_ROUTE[0], tasksAgencyPoints);

        if (!routesFromOfficeToPointToPoint.isEmpty()) {
            return buildingRouteFromPointToPoint(routesFromOfficeToPointToPoint, addressTimesMatrix, timeTask[2], tasksAgencyPoints);
        }
        return null;
    }

    private static List<Route> getRoutesFromOfficeToFourPoints(Office office,
                                                               AddressTimesMatrix addressTimesMatrix,
                                                               int[] timeTask,
                                                               TasksAgencyPoints tasksAgencyPoints) {
        List<Route> routesFromOfficeToPointToPoint = buildingRouteFromOfficeToPointToPoint(office,
                addressTimesMatrix, new int[]{timeTask[0], timeTask[1]}, timeTask[0] + TIME_ROUTE[0], tasksAgencyPoints);

        List<Route> routesFromOfficeToPointToPointToPoint = buildingRouteFromPointToPoint(routesFromOfficeToPointToPoint, addressTimesMatrix, timeTask[2], tasksAgencyPoints);
        if (!routesFromOfficeToPointToPoint.isEmpty()) {
            return buildingRouteFromPointToPoint(routesFromOfficeToPointToPointToPoint, addressTimesMatrix, timeTask[3], tasksAgencyPoints);
        }
        return null;
    }
}