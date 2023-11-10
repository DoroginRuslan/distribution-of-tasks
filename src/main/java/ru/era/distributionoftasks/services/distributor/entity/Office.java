package ru.era.distributionoftasks.services.distributor.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Office implements AddressInterface {

    @Getter
    private final int id;
    @Getter
    @Setter
    private List<IntPointPair> listRoutesSignor = new ArrayList<>();
    @Getter
    @Setter
    private List<IntPointPair> listRoutesMiddle = new ArrayList<>();
    @Getter
    @Setter
    private List<IntPointPair> listRoutesJunior = new ArrayList<>();

    private int addressId;

    @Getter
    private final List<AlgEmployee> employeeList;


    public Office(int id, List<AlgEmployee> employeeList) {
        this.id = id;
        this.employeeList = employeeList;
    }

    @Override
    public int getAddressId() {
        return addressId;
    }

    @Override
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public long getCountEmployees(Rang rang) {
        return employeeList.stream()
                .map(AlgEmployee::getRang)
                .filter(r -> r == rang)
                .count();
    }
}
