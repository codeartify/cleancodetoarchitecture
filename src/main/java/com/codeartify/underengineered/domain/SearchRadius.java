package com.codeartify.underengineered.domain;

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
