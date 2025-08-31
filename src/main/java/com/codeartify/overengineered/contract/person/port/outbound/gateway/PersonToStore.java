package com.codeartify.overengineered.contract.person.port.outbound.gateway;

public record PersonToStore(String id, String firstName, String lastName, String street, String streetNumber, String zip,
                            String location, String country) {
}
