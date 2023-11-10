package ru.era.distributionoftasks.services.distributor.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class AgencyPoint implements AddressInterface {
    private final int databaseId;
    // private String address; //адрес точки
    private final String pointsConnected; // когда подключена точка?
    private final boolean isDelivered;// карты и материалы доставлены?
    private final int numberOfDaysOfIssue;// кол-во дней после выдачи последней карты?
    private final int numberOfApproved;// кол-во одобренных заявок?
    private final int numberOfIssued;// кол-во выданных карт?

    private int addressId;

    @Getter
    @Setter
    private Priority taskPriority;

    public AgencyPoint(int databaseId, String pointsConnected, boolean isDelivered, int numberOfDaysOfIssue, int numberOfApproved, int numberOfIssued) {
        this.databaseId = databaseId;
        //  this.address = address;
        this.pointsConnected = pointsConnected;
        this.isDelivered = isDelivered;
        this.numberOfDaysOfIssue = numberOfDaysOfIssue;
        this.numberOfApproved = numberOfApproved;
        this.numberOfIssued = numberOfIssued;
        this.addressId = addressId;
    }

    public int getDatabaseId() {
        return databaseId;
    }


    public String getPointsConnected() {
        return pointsConnected;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public int getNumberOfDaysOfIssue() {
        return numberOfDaysOfIssue;
    }

    public int getNumberOfApproved() {
        return numberOfApproved;
    }

    public int getNumberOfIssued() {
        return numberOfIssued;
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
