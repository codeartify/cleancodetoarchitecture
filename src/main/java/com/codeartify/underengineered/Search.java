package com.codeartify.underengineered;

public record Search(Location searchLocation, SearchRadius searchRadius) {
    static double square(double value) {
        return value * value;
    }

    static Search from(Double x, Double y, Double searchRadius) {
        return new Search(new Location(x, y), new SearchRadius(searchRadius));
    }

    boolean contains(Property property) {
        var deltaX = property.location().x() - searchLocation().x();
        var deltaY = property.location().y() - searchLocation().y();
        return square(deltaX) + square(deltaY) <= square(searchRadius().value());
    }
}
