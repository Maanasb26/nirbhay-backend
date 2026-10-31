
package com.maanas.nirbhay.dto;

import java.time.LocalDateTime;

public class AdminReportResponse {

    private Long id;
    private String caseId;
    private String category;
    private String description;
    private String location;
    private String status;
    private LocalDateTime createdAt;

    public AdminReportResponse(
            Long id,
            String caseId,
            String category,
            String description,
            String location,
            String status,
            LocalDateTime createdAt) {

        this.id = id;
        this.caseId = caseId;
        this.category = category;
        this.description = description;
        this.location = location;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getCaseId() {
        return caseId;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
