package com.codeartify.overengineered.contract.person.exception;

public class AddressInvalidException extends RuntimeException {

    public AddressInvalidException(String message) {
        super(message);
    }
}
