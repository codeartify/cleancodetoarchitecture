package com.codeartify.underengineered.domain;

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
