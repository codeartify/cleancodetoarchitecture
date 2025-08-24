package com.codeartify.overengineered.module.person.domain;


import com.codeartify.overengineered.contract.person.exception.LocationInvalidException;

public record Location(String value) {

    public Location(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new LocationInvalidException("Location must not be null or empty");
        }
        this.value = value.trim();
    }
}
