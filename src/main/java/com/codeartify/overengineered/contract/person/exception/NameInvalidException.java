package com.codeartify.overengineered.contract.person.exception;

public class NameInvalidException extends RuntimeException {
    public NameInvalidException(String message) {
        super(message);
    }
}
