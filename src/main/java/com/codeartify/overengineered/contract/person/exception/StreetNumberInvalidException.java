package com.codeartify.overengineered.contract.person.exception;

public class StreetNumberInvalidException extends RuntimeException {
    public StreetNumberInvalidException(String message) {
        super(message);
    }
}
