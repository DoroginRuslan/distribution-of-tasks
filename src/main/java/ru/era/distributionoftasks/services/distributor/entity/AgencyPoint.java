package ru.era.distributionoftasks.services.distributor.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class AgencyPoint implements AddressInterface {
    private final int databaseId;           // Уникальный идентификатор
    private final String pointsConnected; // когда подключена точка?
    private final boolean isDelivered;// карты и материалы доставлены?
    private final int numberOfDaysOfIssue;// кол-во дней после выдачи последней карты?
    private final int numberOfApproved;// кол-во одобренных заявок?
    private final int numberOfIssued;// кол-во выданных карт?
    @Setter
    private int addressId;
    @Setter
    private Task task;

    public AgencyPoint(int databaseId, String pointsConnected, boolean isDelivered, int numberOfDaysOfIssue, int numberOfApproved, int numberOfIssued) {
        this.databaseId = databaseId;
        //  this.address = address;
        this.pointsConnected = pointsConnected;
        this.isDelivered = isDelivered;
        this.numberOfDaysOfIssue = numberOfDaysOfIssue;
        this.numberOfApproved = numberOfApproved;
        this.numberOfIssued = numberOfIssued;
    }
}
