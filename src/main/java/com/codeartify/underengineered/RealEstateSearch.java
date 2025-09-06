package com.codeartify.underengineered;

import java.util.List;

import static java.util.stream.Collectors.toList;

public record RealEstateSearch(Location searchLocation, SearchRadius searchRadius) {
    List<Long> findContained(List<RealEstate> realEstate) {
        return realEstate.stream()
                .filter(this::contains)
                .map(RealEstate::id)
                .collect(toList());
    }

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
