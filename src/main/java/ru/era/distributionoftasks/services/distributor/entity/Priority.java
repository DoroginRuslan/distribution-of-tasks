package ru.era.distributionoftasks.services.distributor.entity;

import lombok.Getter;

@Getter
public enum Priority {
    MAX_PRIORITY(0),
    MEDIUM_PRIORITY(1),
    LOW_PRIORITY(2)
    ;
    private final int priority;

    Priority(int priority) {
        this.priority = priority;
    }
}
