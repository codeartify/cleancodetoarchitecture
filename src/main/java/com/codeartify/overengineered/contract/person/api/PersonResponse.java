package com.codeartify.overengineered.contract.person.api;


public record PersonResponse(String id, String salutation, String firstName, String lastName, String street, String streetNumber,
                             String zip, String location, String country) {


}
