
package com.maanas.nirbhay.controller;

import com.maanas.nirbhay.dto.IncidentReportRequest;
import com.maanas.nirbhay.model.IncidentReport;
import com.maanas.nirbhay.service.IncidentReportService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class IncidentReportController {

    private final IncidentReportService incidentReportService;

    public IncidentReportController(
            IncidentReportService incidentReportService) {
        this.incidentReportService = incidentReportService;
    }

    @PostMapping
    public ResponseEntity<IncidentReport> submitReport(
            @Valid @RequestBody IncidentReportRequest request) {

        IncidentReport report = new IncidentReport();
        report.setCategory(request.getCategory());
        report.setDescription(request.getDescription());
        report.setLocation(request.getLocation());

        IncidentReport savedReport =
                incidentReportService.submitReport(report);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedReport);
    }
}
