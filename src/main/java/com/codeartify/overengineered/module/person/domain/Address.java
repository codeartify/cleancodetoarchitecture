package com.codeartify.overengineered.module.person.domain;


import com.codeartify.overengineered.contract.person.exception.AddressInvalidException;

public record Address(Street street, StreetNumber streetNumber, Zip zip, Location location, Country country) {

    public Address {
        if (street == null || streetNumber == null || zip == null || location == null || country == null) {
            throw new AddressInvalidException("Address fields must not be null");
        }
    }
}
