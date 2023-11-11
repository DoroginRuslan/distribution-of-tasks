package ru.era.distributionoftasks.services.distributor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlgEmployee {
    public final static int WORK_DAY_IN_MINUTES = 480;
    private final Long databaseId;
    private final Rang rang;
}
