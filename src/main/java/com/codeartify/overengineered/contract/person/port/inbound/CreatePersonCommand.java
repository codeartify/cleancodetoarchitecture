package com.codeartify.overengineered.contract.person.port.inbound;

public record CreatePersonCommand(String salutation, String firstName, String lastName, String street, String streetNumber, String zip,
                                  String location, String country) {

}
