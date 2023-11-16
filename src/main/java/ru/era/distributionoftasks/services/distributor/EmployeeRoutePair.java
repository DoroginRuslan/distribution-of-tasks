package ru.era.distributionoftasks.services.distributor;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.era.distributionoftasks.services.distributor.entity.AlgEmployee;
import ru.era.distributionoftasks.services.distributor.entity.Route;

@Data
@AllArgsConstructor
public class EmployeeRoutePair {
    private AlgEmployee algEmployee;
    private Route route;
}
