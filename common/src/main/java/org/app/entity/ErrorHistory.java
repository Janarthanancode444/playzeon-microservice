package org.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.Instant;

@Entity
@Table(name = "error_history")
public class ErrorHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "error_message")
    private String errorMessage;
    @Column(name = "end_point")
    private String endPoint;
    @Column(name = "exception_class")
    private String exceptionClass;
    @Column(name = "error_class")
    private String errorClass;
    @Column(name = "method_name")
    private String methodName;
    @Column(name = "error_occurred", columnDefinition = "TIMESTAMP(6)")
    @CurrentTimestamp
    private Instant errorOccurred;
    @Column(name = "userId")
    private String userId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public String getErrorClass() {
        return errorClass;
    }

    public void setErrorClass(String errorClass) {
        this.errorClass = errorClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Instant getErrorOccurred() {
        return errorOccurred;
    }

    public void setErrorOccurred(Instant errorOccurred) {
        this.errorOccurred = errorOccurred;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
