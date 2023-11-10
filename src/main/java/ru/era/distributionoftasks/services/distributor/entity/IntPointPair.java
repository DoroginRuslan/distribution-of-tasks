package ru.era.distributionoftasks.services.distributor.entity;

import lombok.Data;

import java.util.List;

@Data
public class IntPointPair {
    Integer lastTime;
    List<AgencyPoint> agencyPointList;
}
