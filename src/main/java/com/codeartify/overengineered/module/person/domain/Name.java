package com.codeartify.overengineered.module.person.domain;


import com.codeartify.overengineered.contract.person.exception.NameInvalidException;

public record Name(String value) {

    public Name(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new NameInvalidException("Name must not be null or empty");
        }
        this.value = value.trim();
    }
}
