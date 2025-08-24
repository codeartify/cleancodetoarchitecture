package com.codeartify.overengineered.module.person.domain;


import com.codeartify.overengineered.contract.person.exception.PersonInvalidException;

public record Person(Names names, Address address) {
    public Person {
        if (names == null || address == null) {
            throw new PersonInvalidException("Names and Address must not be null");
        }
    }
}
