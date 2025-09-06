package com.codeartify.underengineered;

public record RealEstateSearch(Location searchLocation, SearchRadius searchRadius) {
    static double square(double value) {
        return value * value;
    }

    static RealEstateSearch from(Double x, Double y, Double searchRadius) {
        return new RealEstateSearch(new Location(x, y), new SearchRadius(searchRadius));
    }

    boolean contains(RealEstate realEstate) {
        var deltaX = realEstate.location().x() - searchLocation().x();
        var deltaY = realEstate.location().y() - searchLocation().y();
        return square(deltaX) + square(deltaY) <= square(searchRadius().value());
    }
}
