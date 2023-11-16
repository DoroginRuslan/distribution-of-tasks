package ru.era.distributionoftasks.graphhopper.jsonobjects;

import lombok.Data;

import java.util.List;

@Data
public class MatrixWeightsAnswer {
    List<List<Integer>> distances;
    List<List<Integer>> times;
    List<List<Float>> weights;
}
