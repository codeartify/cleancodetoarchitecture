package com.codeartify.overengineered.contract.person.exception;

public class ZipCodeInvalidException extends RuntimeException {
    public ZipCodeInvalidException(String message) {
        super(message);
    }
}
