package org.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.Instant;

@Entity
public class Error {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "error_message")
    private String errorMessage;
    @Column(name = "end_point")
    private String endPoint;
    @Column(name = "type")
    private Integer type;
    @Column(name = "method_name")
    private String methodName;
    @Column(name = "error_created_at")
    @CurrentTimestamp
    private Instant errorCreatedAt;
    @Column(name = "created_by", nullable = true)
    private String createdBy;

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Instant getErrorCreatedAt() {
        return errorCreatedAt;
    }

    public void setErrorCreatedAt(Instant errorCreatedAt) {
        this.errorCreatedAt = errorCreatedAt;
    }
}
