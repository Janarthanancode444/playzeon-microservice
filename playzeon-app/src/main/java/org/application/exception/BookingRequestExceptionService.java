package org.application.exception;

public class BookingRequestExceptionService extends RuntimeException {
    public BookingRequestExceptionService(final String message) {
        super(message);
    }
}
