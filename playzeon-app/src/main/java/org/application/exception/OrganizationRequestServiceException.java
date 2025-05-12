package org.application.exception;

public class OrganizationRequestServiceException extends RuntimeException {
    private String endMessage;
    private String endPoint;
    private String createdBy;
    private String methodName;
    private Integer type;


    public OrganizationRequestServiceException(String message, String endMessage, String endPoint, String createdBy,Integer type,String methodName) {
        super(message);
        this.endMessage = endMessage;
        this.endPoint = endPoint;
        this.createdBy = createdBy;
        this.type=type;
        this.methodName=methodName;
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

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
