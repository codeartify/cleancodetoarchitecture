package com.codeartify.overengineered.module.person.domain;

import com.codeartify.overengineered.contract.person.exception.NamesInvalidException;

public record Names(Salutation salutation, Name firstName, Name lastName) {
    public Names {
        if (firstName == null || lastName == null) {
            throw new NamesInvalidException("First name and last name must not be null");
        }
        if (salutation == null) {
            salutation = Salutation.UNKNOWN;
        }
    }

}
