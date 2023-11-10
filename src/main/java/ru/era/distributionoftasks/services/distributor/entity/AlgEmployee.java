package ru.era.distributionoftasks.services.distributor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlgEmployee {
    private final Long databaseId;
    private final Rang rang;
}
