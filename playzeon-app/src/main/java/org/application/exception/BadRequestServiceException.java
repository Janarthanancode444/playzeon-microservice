package org.application.exception;

public class BadRequestServiceException extends RuntimeException{

    public BadRequestServiceException(final String message) {
        super(message);
    }

}