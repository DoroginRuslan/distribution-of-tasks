package ru.era.distributionoftasks.services.distributor.entity;

import lombok.Getter;
import ru.era.distributionoftasks.entities.Employee;

import java.util.*;

public class Office implements AddressInterface {

    @Getter
    private final int id;
//    @Getter
//    Map<AlgEmployee, List<Route>> employeeRoutesVariantsMap;
    @Getter
    private final List<AlgEmployee> employees;
    private int addressId;


    public Office(int id, List<AlgEmployee> employeeList) {
        this.id = id;
        employees = employeeList;
//        employeeRoutesVariantsMap = new HashMap<>(employeeList.size());
//        for(AlgEmployee algEmployee : employeeList) {
//            employeeRoutesVariantsMap.put(algEmployee, new ArrayList<>());
//        }
    }

//    public Set<AlgEmployee> getEmployees() {
//        return employeeRoutesVariantsMap.keySet();
//    }

    @Override
    public int getAddressId() {
        return addressId;
    }

    @Override
    public void setAddressIdImpl(int addressId) {
        this.addressId = addressId;
    }
}
