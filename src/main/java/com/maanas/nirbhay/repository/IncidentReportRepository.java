
package com.maanas.nirbhay.repository;

import com.maanas.nirbhay.model.IncidentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentReportRepository
        extends JpaRepository<IncidentReport, Long> {
}
