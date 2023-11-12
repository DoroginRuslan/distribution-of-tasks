package ru.era.distributionoftasks.services.entities;

import lombok.Data;
import ru.era.distributionoftasks.entities.Bank;
import ru.era.distributionoftasks.graphhopper.jsonobjects.MatrixWeightsAnswer;

import java.util.List;

@Data
public class MatrixWeightWithBanks {
    List<Bank> banksOrder;
    MatrixWeightsAnswer matrixWeightsAnswer;
}
