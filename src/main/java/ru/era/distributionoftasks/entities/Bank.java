package ru.era.distributionoftasks.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Entity
@Data
@Accessors(chain = true)
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;
    private String registrationDate;
    private boolean materialsDelivered;
    private int lastCardIssuanceDays;
    private int approvedApplicationsNum;
    private int issuanceCardsNum;
    private String latitude;
    private String longitude;
}
