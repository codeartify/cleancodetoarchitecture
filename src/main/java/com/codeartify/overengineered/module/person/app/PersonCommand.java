package com.codeartify.overengineered.module.person.app;

public record PersonCommand(String firstName, String lastName, String street, String streetNumber, String zip,
                            String location, String country) {

}
