package com.codeartify.overengineered.module.person.domain;


import com.codeartify.overengineered.contract.person.exception.StreetInvalidException;

public record Street(String value) {

    public Street(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new StreetInvalidException("Street must not be null or empty");
        }
        this.value = value.trim();
    }
}
