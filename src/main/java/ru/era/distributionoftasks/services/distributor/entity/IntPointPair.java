package ru.era.distributionoftasks.services.distributor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class IntPointPair {
    Integer lastTime;
    List<AgencyPoint> agencyPointList;
}
