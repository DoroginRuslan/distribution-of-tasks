package ru.era.distributionoftasks.services.distributor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeRoute {
    private final AlgEmployee algEmployee;
    private final List<AgencyPoint> agencyPointList;
}
