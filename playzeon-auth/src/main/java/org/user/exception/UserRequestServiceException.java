package org.user.exception;

public class UserRequestServiceException extends RuntimeException {
    private String endMessage;

    private String endPoint;
    private String createdBy;

    public UserRequestServiceException(String message, String endMessage, String endPoint, String createdBy) {
        super(message);
        this.endMessage = endMessage;
        this.endPoint = endPoint;
        this.createdBy = createdBy;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getEndMessage() {
        return endMessage;
    }

    public void setEndMessage(String endMessage) {
        this.endMessage = endMessage;
    }
}

