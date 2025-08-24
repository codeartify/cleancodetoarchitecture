package com.codeartify.overengineered.module.person.domain;


import com.codeartify.overengineered.contract.person.exception.ZipCodeInvalidException;

public record Zip(String value) {
    public Zip {
        if (value == null || !value.matches("\\d{5}")) {
            throw new ZipCodeInvalidException("Zip must be a valid 5-digit number");
        }
    }
}
