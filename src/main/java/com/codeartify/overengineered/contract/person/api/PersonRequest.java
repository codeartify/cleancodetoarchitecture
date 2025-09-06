package com.codeartify.overengineered.contract.person.api;


public record PersonRequest(String salutation, String firstName, String lastName, String street, String streetNumber,
                            String zip, String location, String country) {


}
