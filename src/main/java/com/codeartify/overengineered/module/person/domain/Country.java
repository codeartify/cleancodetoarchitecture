package com.codeartify.overengineered.module.person.domain;

import com.codeartify.overengineered.contract.person.exception.CountryInvalidException;

public record Country(String value) {

    public Country(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new CountryInvalidException("Country must not be null or empty");
        }
        this.value = value.trim();
    }
}
