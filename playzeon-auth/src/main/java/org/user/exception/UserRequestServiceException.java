package org.user.exception;

import java.time.Instant;

public class UserRequestServiceException extends RuntimeException {
    private String errorMessage;

    private String endPoint;
    private String errorClass;
    private String exceptionClass;
    private String userId;
    private Instant errorOccurred;
    private String methodName;


    public UserRequestServiceException(String message, String errorMessage, String endPoint, String errorClass, String methodName, String userId, String exceptionClass) {
        super(message);
        this.errorClass = errorClass;
        this.endPoint = endPoint;
        this.userId = userId;
        this.methodName = methodName;
        this.exceptionClass = exceptionClass;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getErrorClass() {
        return errorClass;
    }

    public void setErrorClass(String errorClass) {
        this.errorClass = errorClass;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getErrorOccurred() {
        return errorOccurred;
    }

    public void setErrorOccurred(Instant errorOccurred) {
        this.errorOccurred = errorOccurred;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}

