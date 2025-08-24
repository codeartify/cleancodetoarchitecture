package com.codeartify.overengineered.module.person.domain;


import com.codeartify.overengineered.contract.person.exception.StreetNumberInvalidException;

public record StreetNumber(String value) {

    public StreetNumber(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new StreetNumberInvalidException("Street number must not be null or empty");
        }
        this.value = value.trim();
    }
}
