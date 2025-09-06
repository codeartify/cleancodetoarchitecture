package com.codeartify.underengineered.realestatesearch.domain;

import com.codeartify.underengineered.domain.Location;

import java.util.List;

import static java.util.stream.Collectors.toList;

public record RealEstateSearch(Location searchLocation, SearchRadius searchRadius) {
    public List<Long> findContained(List<RealEstate> realEstates) {
        return realEstates.stream()
                .filter(this::contains)
                .map(RealEstate::id)
                .collect(toList());
    }

    static double square(double value) {
        return value * value;
    }

    public static RealEstateSearch from(Double x, Double y, Double searchRadius) {
        return new RealEstateSearch(new Location(x, y), new SearchRadius(searchRadius));
    }

    boolean contains(RealEstate realestate) {
        var deltaX = realestate.location().x() - searchLocation().x();
        var deltaY = realestate.location().y() - searchLocation().y();
        return square(deltaX) + square(deltaY) <= square(searchRadius().value());
    }
}
