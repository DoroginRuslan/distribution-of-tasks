package ru.era.distributionoftasks.services.distributor.entity;

import lombok.Getter;

@Getter
public enum Rang {
    SENIOR_RANG(0),
    MIDDLE_RANG(1),
    JUNIOR_RANG(2)
    ;

    private final int rang;
    Rang(int rang) {
        this.rang = rang;
    }
}
