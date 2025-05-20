package org.application.dto;

import java.time.Instant;

public class SportCenterMapDTO {
    private String centerId;
    private String sportsId;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getSportsId() {
        return sportsId;
    }

    public void setSportsId(String sportsId) {
        this.sportsId = sportsId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
