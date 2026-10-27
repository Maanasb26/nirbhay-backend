
package com.maanas.nirbhay.service;

import com.maanas.nirbhay.model.IncidentReport;
import com.maanas.nirbhay.repository.IncidentReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IncidentReportService {

    private final IncidentReportRepository incidentReportRepository;

    public IncidentReportService(
            IncidentReportRepository incidentReportRepository) {
        this.incidentReportRepository = incidentReportRepository;
    }

    @Transactional
    public IncidentReport submitReport(IncidentReport report) {

        if (report.getCategory() == null ||
                report.getCategory().isBlank()) {
            throw new IllegalArgumentException(
                    "Report category is required");
        }

        if (report.getDescription() == null ||
                report.getDescription().isBlank()) {
            throw new IllegalArgumentException(
                    "Report description is required");
        }

        report.setCategory(report.getCategory().trim());
        report.setDescription(report.getDescription().trim());

        if (report.getLocation() != null) {
            report.setLocation(report.getLocation().trim());
        }

        return incidentReportRepository.save(report);
    }
}
