package com.codeartify.underengineered;

import java.util.List;

import static java.util.stream.Collectors.toList;

public record PropertySearch(Location searchLocation, SearchRadius searchRadius) {
    List<Long> findContained(List<Property> properties) {
        return properties.stream()
                .filter(this::contains)
                .map(Property::id)
                .collect(toList());
    }

    static double square(double value) {
        return value * value;
    }

    static PropertySearch from(Double x, Double y, Double searchRadius) {
        return new PropertySearch(new Location(x, y), new SearchRadius(searchRadius));
    }

    boolean contains(Property property) {
        var deltaX = property.location().x() - searchLocation().x();
        var deltaY = property.location().y() - searchLocation().y();
        return square(deltaX) + square(deltaY) <= square(searchRadius().value());
    }
}
