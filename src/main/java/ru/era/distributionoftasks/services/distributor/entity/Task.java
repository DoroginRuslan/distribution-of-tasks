package ru.era.distributionoftasks.services.distributor.entity;

import lombok.Data;

import java.util.List;

@Data
public class Task {
    private final int timeInMinutes;
    private final Priority priority;
    private final List<Rang> rangList;
    private final int profit;

    private boolean checkAvailableForRank(Rang rang) {
        return rangList.contains(rang);
    }
}
