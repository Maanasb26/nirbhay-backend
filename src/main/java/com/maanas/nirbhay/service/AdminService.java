
package com.maanas.nirbhay.service;

import com.maanas.nirbhay.dto.AdminReportResponse;
import com.maanas.nirbhay.model.IncidentReport;
import com.maanas.nirbhay.repository.IncidentReportRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final IncidentReportRepository repository;

    public AdminService(IncidentReportRepository repository) {
        this.repository = repository;
    }

    public List<AdminReportResponse> getAllReports() {

        return repository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private AdminReportResponse convertToResponse(
            IncidentReport report) {

        return new AdminReportResponse(
                report.getId(),
                report.getCaseId(),
                report.getCategory(),
                report.getDescription(),
                report.getLocation(),
                report.getStatus(),
                report.getCreatedAt()
        );
    }
}
