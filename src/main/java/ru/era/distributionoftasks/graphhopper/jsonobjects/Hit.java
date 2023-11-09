package ru.era.distributionoftasks.graphhopper.jsonobjects;

import lombok.Data;

@Data
public class Hit {
    Point point;
    String name;
    String country;
    String countrycode;
    String osm_key;
    String osm_value;
}
