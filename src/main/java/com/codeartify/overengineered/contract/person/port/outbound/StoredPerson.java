package com.codeartify.overengineered.contract.person.port.outbound;

public record StoredPerson(String id, String salutation, String firstName, String lastName, String street, String streetNumber, String zip,
                           String location, String country) {
}
