package ru.era.distributionoftasks.services.distributor;

import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class DistributorConnector {
    @Autowired
    private TaskTypeService taskTypeService;
    @Autowired
    private TaskLogService taskLogService;
    @Autowired
    private YandexGeocoderService yandexGeocoderService;
    @Autowired
    private RoutesService routesService;

    public DistributorConnector(TaskTypeService taskTypeService, TaskLogService taskLogService, YandexGeocoderService yandexGeocoderService, RoutesService routesService) {
        this.taskTypeService = taskTypeService;
        this.taskLogService = taskLogService;
        this.yandexGeocoderService = yandexGeocoderService;
        this.routesService = routesService;
    }

    int countAddresses;
    Map<String, Integer> addressIdMap;


    public List<TaskLog> getData(List<Employee> employees, List<Bank> bankList, Map<Bank, Integer> overdueBanks, List<Long> nonDistributed) {
        countAddresses = 0;
        addressIdMap = new HashMap<>();

        // Предварительная настройка сотрудников
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

        // Предварительная настройка банков
        List<Office> offices = new ArrayList<>(countAddresses);
        for(int i = 0; i < countAddresses; i++) {
            Office office = new Office(i, algEmployees.get(i));
            office.setAddressIdImpl(i);
            offices.add(office);
        }

        List<AgencyPoint> agencyPointList = new ArrayList<>(bankList.size());
        for(Bank bank : bankList) {
            int addressAgencyPointId = addAddressInMap(bank.getAddress());
            Integer overdueDays = overdueBanks.get(bank);
            agencyPointList.add(AgencyPoint.of(bank,
                    (overdueDays == null) ? 0 : overdueDays,
                    addressAgencyPointId));
        }

        AddressTimesMatrix addressTimeMatrix = getAddressMatrix();
        ServiceTaskAssignment serviceTaskAssignment = new ServiceTaskAssignment(addressTimeMatrix, agencyPointList, offices);
        List<EmployeeRoutePair> result = serviceTaskAssignment.calcEmployeeRoutes(nonDistributed);

        List<TaskLog> taskLogList = new ArrayList<>();
        Map<Priority, TaskType> stringTaskTypeMap = new HashMap<>();
        stringTaskTypeMap.put(Priority.MAX_PRIORITY, taskTypeService.getTaskTypeByName("Выезд на точку для стимулирования выдач"));
        stringTaskTypeMap.put(Priority.MEDIUM_PRIORITY, taskTypeService.getTaskTypeByName("Обучение агента"));
        stringTaskTypeMap.put(Priority.LOW_PRIORITY, taskTypeService.getTaskTypeByName("Доставка карт и материалов"));
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
        for(String address : addressIdMap.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).map(Map.Entry::getKey).toList()) {
            System.out.println(address);
            GeoPoint geoPoint = yandexGeocoderService.sendRequestForConverting(address);
            Point point = new Point().setLat(String.valueOf(geoPoint.lat)).setLng(String.valueOf(geoPoint.lon));
            points.add(point);
        }
        MatrixWeightsAnswer matrixWeightsAnswer = routesService.getMatrixWeightsAnswers(points);
        int[][] times = new int[countAddresses][countAddresses];
        for(int i = 0; i < matrixWeightsAnswer.getTimes().size(); i++) {
            for(int j = 0; j < matrixWeightsAnswer.getTimes().get(i).size(); j++) {
                times[i][j] = (int) (1.0 * matrixWeightsAnswer.getTimes().get(i).get(j) / 60.0);
            }
        }
        return new AddressTimesMatrix(times);
    }
}
