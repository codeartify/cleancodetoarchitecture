package com.codeartify.underengineered;

public record Location(Double x, Double y) {
    public Location {
        if (x == null) {
            throw new RuntimeException("x is missing");
        }
        if (y == null) {
            throw new RuntimeException("y is missing");
        }
    }
}
