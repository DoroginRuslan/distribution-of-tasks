package ru.era.distributionoftasks.services.distributor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.entities.Bank;
import ru.era.distributionoftasks.entities.Employee;
import ru.era.distributionoftasks.entities.TaskLog;
import ru.era.distributionoftasks.entities.TaskType;
import ru.era.distributionoftasks.graphhopper.RoutesService;
import ru.era.distributionoftasks.graphhopper.jsonobjects.MatrixWeightsAnswer;
import ru.era.distributionoftasks.graphhopper.jsonobjects.Point;
import ru.era.distributionoftasks.services.TaskLogService;
import ru.era.distributionoftasks.services.TaskTypeService;
import ru.era.distributionoftasks.services.distributor.entity.*;
import ru.era.distributionoftasks.yandexgeocoder.GeoPoint;
import ru.era.distributionoftasks.yandexgeocoder.YandexGeocoderService;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DistributorConnector {
    @Autowired
    TaskTypeService taskTypeService;

    @Autowired
    TaskLogService taskLogService;

    int countAddresses = 0;
    Map<String, Integer> addressIdMap = new HashMap<>();


    public List<TaskLog> getData(List<Employee> employees, List<Bank> banks) {
        for(Employee employee : employees) {
            addAddressInMap(employee.getAddress());
        }
        List<List<AlgEmployee>> algEmployees = new ArrayList<>();
        for(int i = 0; i < countAddresses; i++) {
            algEmployees.add(new ArrayList<>());
        }

        Map<String, Rang> stringRangMap = new HashMap<>();
        stringRangMap.put("Синьор", Rang.SENIOR_RANG);
        stringRangMap.put("Мидл", Rang.MIDDLE_RANG);
        stringRangMap.put("Джун", Rang.JUNIOR_RANG);
        for(Employee employee : employees) {
            int id = addressIdMap.get(employee.getAddress());
            AlgEmployee algEmployee = new AlgEmployee(employee.getId(), stringRangMap.get(employee.getGrade().getName()));
            algEmployees.get(id).add(algEmployee);
        }
        List<Office> offices = new ArrayList<>(countAddresses);
        for(int i = 0; i < countAddresses; i++) {
            Office office = new Office(i, algEmployees.get(i));
            office.setAddressId(i);
            offices.add(office);
        }

        List<AgencyPoint> agencyPointList = new ArrayList<>(banks.size());
        for(Bank bank : banks) {
            int addressAgencyPointId = addAddressInMap(bank.getAddress());
            agencyPointList.add(new AgencyPoint(
                    (int) bank.getId(),
                    addressAgencyPointId,
                    bank.getRegistrationDate(),
                    bank.isMaterialsDelivered(),
                    bank.getLastCardIssuanceDays(),
                    bank.getApprovedApplicationsNum(),
                    bank.getIssuanceCardsNum()));
        }

        AddressTimesMatrix addressTimeMatrix = getAddressMatrix();
        ServiceTaskAssignment serviceTaskAssignment = new ServiceTaskAssignment(addressTimeMatrix, agencyPointList, offices);
        List<EmployeeRoutePair> result = serviceTaskAssignment.calcEmployeeRoutes();
        List<TaskLog> taskLogList = new ArrayList<>();
        Map<Rang, TaskType> stringTaskTypeMap = new HashMap<>();
        stringTaskTypeMap.put(Rang.SENIOR_RANG, taskTypeService.getTaskTypeByName("Выезд на точку для стимулирования выдач"));
        stringTaskTypeMap.put(Rang.MIDDLE_RANG, taskTypeService.getTaskTypeByName("Обучение агента"));
        stringTaskTypeMap.put(Rang.JUNIOR_RANG, taskTypeService.getTaskTypeByName("Доставка карт и материалов"));
        for(EmployeeRoutePair employeeRoutePair : result) {
            for(AgencyPoint agencyPoint : employeeRoutePair.getRoute().getAgencyPointList()) {
                TaskLog taskLog = new TaskLog()
                        .setEmployee(new Employee().setId(employeeRoutePair.getAlgEmployee().getDatabaseId()))
                        .setTaskType(stringTaskTypeMap.get(agencyPoint.getTask().getPriority()))
                        .setTaskSetDate(LocalDateTime.now())
                        .setBank(new Bank().setId(agencyPoint.getDatabaseId()))
                        .setCompleted(false);
                taskLogService.addTaskLog(taskLog);
            }
        }
        return taskLogList;
    }

    private int addAddressInMap(String s) {
        if(!addressIdMap.containsKey(s)) {
            addressIdMap.put(s, countAddresses++);
        }
        return countAddresses-1;
    }

    private AddressTimesMatrix getAddressMatrix() {
        List<Point> points = new ArrayList<>();
        YandexGeocoderService yandexGeocoderService = new YandexGeocoderService();
        for(String address : addressIdMap.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).map(Map.Entry::getKey).toList()) {
            System.out.println(address);
            GeoPoint geoPoint = yandexGeocoderService.sendRequestForConverting(address);
            Point point = new Point().setLat(String.valueOf(geoPoint.lat)).setLng(String.valueOf(geoPoint.lon));
            points.add(point);
        }
        MatrixWeightsAnswer matrixWeightsAnswer = new RoutesService().getMatrixWeightsAnswers(points);
        int[][] times = new int[countAddresses][countAddresses];
        for(int i = 0; i < matrixWeightsAnswer.getTimes().size(); i++) {
            for(int j = 0; j < matrixWeightsAnswer.getTimes().get(i).size(); j++) {
                times[i][j] = (int) (1.0 * matrixWeightsAnswer.getTimes().get(i).get(j) / 60.0);
            }
        }
        return new AddressTimesMatrix(times);
    }
}
