package ru.era.distributionoftasks.graphhopper;

import java.io.IOException;

public class GraphhopperErrorException extends RuntimeException {
    public GraphhopperErrorException(String string) {
        super(string);
    }

    public GraphhopperErrorException(String string, IOException e) {
        super(string, e);
    }
}
