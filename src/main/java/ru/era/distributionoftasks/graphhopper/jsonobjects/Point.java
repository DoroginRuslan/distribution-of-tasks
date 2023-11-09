package ru.era.distributionoftasks.graphhopper.jsonobjects;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Point {
    String lat;
    String lng;

    public String getByWebParam() {
        return lat+","+lng;
    }
}
