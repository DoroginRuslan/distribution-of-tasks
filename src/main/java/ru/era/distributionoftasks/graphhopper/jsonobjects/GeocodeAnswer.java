package ru.era.distributionoftasks.graphhopper.jsonobjects;

import lombok.Data;

import java.util.List;

@Data
public class GeocodeAnswer {
    List<Hit> hits;
}
