package ru.era.distributionoftasks.services.distributor.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Office implements AddressInterface {

    private int id;
    private List<List<AgencyPoint>> listRoutesSignor = new ArrayList<>();
    private List<List<AgencyPoint>> listRoutesMiddle = new ArrayList<>();
    private List<List<AgencyPoint>> listRoutesJunior = new ArrayList<>();

    private int[] listDisFromOfficeToHighTask;
    private int[] listDisFromOfficeToMiddleTask;
    private int[] listDisFromOfficeToLowTask;

    private int addressId;

    @Getter
    private final List<AlgEmployee> employeeList;


    public Office(int id, List<AlgEmployee> employeeList) {
        this.id = id;
        this.employeeList = employeeList;
    }

    public int getId() {
        return id;
    }

    public List<List<AgencyPoint>> getListRoutesSignor() {
        return listRoutesSignor;
    }

    public void setListRoutesSignor(List<List<AgencyPoint>> listRoutesSignor) {
        this.listRoutesSignor = listRoutesSignor;
    }

    public List<List<AgencyPoint>> getListRoutesMiddle() {
        return listRoutesMiddle;
    }

    public void setListRoutesMiddle(List<List<AgencyPoint>> listRoutesMiddle) {
        this.listRoutesMiddle = listRoutesMiddle;
    }

    public List<List<AgencyPoint>> getListRoutesJunior() {
        return listRoutesJunior;
    }

    public void setListRoutesJunior(List<List<AgencyPoint>> listRoutesJunior) {
        this.listRoutesJunior = listRoutesJunior;
    }

    public int[] getListDisFromOfficeToHighTask() {
        return listDisFromOfficeToHighTask;
    }

    public void addListDisFromOfficeToHighTask(int[] distance) {
        this.listDisFromOfficeToHighTask = distance;
    }

    public int[] getListDisFromOfficeToMediumTask() {
        return listDisFromOfficeToMiddleTask;
    }

    public void addListDisFromOfficeToMiddleTask(int[] distance) {
        this.listDisFromOfficeToMiddleTask = distance;
    }

    public int[] getListDisFromOfficeToLowTask() {
        return listDisFromOfficeToLowTask;
    }

    public void addListDisFromOfficeToLowTask(int[] distance) {
        this.listDisFromOfficeToLowTask = distance;
    }

    @Override
    public int getAddressId() {
        return addressId;
    }

    @Override
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
