package com.codeartify.underengineered;

public record SearchRadius(Double value) {
    public SearchRadius {
        if (value == null) {
            throw new RuntimeException("radius is missing");
        }
        if (value <= 0) {
            throw new RuntimeException("radius must be greater than 0");
        }
    }
}
