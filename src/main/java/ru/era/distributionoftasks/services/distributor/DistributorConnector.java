package ru.era.distributionoftasks.services.distributor;

import ru.era.distributionoftasks.entities.Bank;
import ru.era.distributionoftasks.entities.Employee;
import ru.era.distributionoftasks.entities.TaskLog;
import ru.era.distributionoftasks.services.distributor.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistributorConnector {
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
            offices.add(new Office(i, algEmployees.get(i)));
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

        AddressTimesMatrix addressTimeMatrix = new AddressTimesMatrix(null);
        ServiceTaskAssignment serviceTaskAssignment = new ServiceTaskAssignment(addressTimeMatrix, agencyPointList, offices);
        return null;
    }

    private int addAddressInMap(String s) {
        if(!addressIdMap.containsKey(s)) {
            addressIdMap.put(s, countAddresses++);
        }
        return countAddresses-1;
    }
}
