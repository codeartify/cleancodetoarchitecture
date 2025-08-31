package com.codeartify.overengineered.contract.person.port.outbound.presenter;

public record PresentablePerson(String id, String firstName, String lastName, String street, String streetNumber,
                                String zip,
                                String location, String country) {
}
