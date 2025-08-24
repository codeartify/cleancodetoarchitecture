package com.codeartify.overengineered.module.person.presentation;


public record PersonRequest(String firstName, String lastName, String street, String streetNumber, String zip,
                            String location, String country) {


}
