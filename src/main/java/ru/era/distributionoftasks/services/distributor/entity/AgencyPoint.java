package ru.era.distributionoftasks.services.distributor.entity;

import lombok.*;
import lombok.experimental.Accessors;
import ru.era.distributionoftasks.entities.Bank;

import java.util.Objects;

@Data
@Accessors(chain = true)
public class AgencyPoint implements AddressInterface {
    private long databaseId;           // Уникальный идентификатор
    private String pointsConnected; // когда подключена точка?
    private boolean isDelivered;// карты и материалы доставлены?
    private int numberOfDaysOfIssue;// кол-во дней после выдачи последней карты?
    private int numberOfApproved;// кол-во одобренных заявок?
    private int numberOfIssued;// кол-во выданных карт?
    private int overdue;          // Кол-во просроченных дней
    private int addressId;
    private Task task;

    public static AgencyPoint of(Bank bank, int overdue, int addressId) {
        return new AgencyPoint()
                .setDatabaseId(bank.getId())
                .setPointsConnected(bank.getRegistrationDate())
                .setDelivered(bank.isMaterialsDelivered())
                .setNumberOfDaysOfIssue(bank.getLastCardIssuanceDays())
                .setNumberOfApproved(bank.getApprovedApplicationsNum())
                .setNumberOfIssued(bank.getIssuanceCardsNum())
                .setOverdue(overdue)
                .setAddressId(addressId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgencyPoint that = (AgencyPoint) o;
        return databaseId == that.databaseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(databaseId);
    }

    @Override
    public void setAddressIdImpl(int addressId) {
        this.addressId = addressId;
    }
}
