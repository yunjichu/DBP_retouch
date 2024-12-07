package Facility.reservation.DBP.controller;

import Facility.reservation.DBP.domain.Report;
import Facility.reservation.DBP.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;
    private final ReservationService reservationService;

    public ReportController(ReportService reportService, ReservationService reservationService) {
        this.reportService = reportService;
        this.reservationService = reservationService;
    }

    @PostMapping("/add")
    public void addReport(@RequestBody Report report) {
        // Validate reservationId
        if (!reservationService.existsById(report.getReservationId())) {
            throw new IllegalArgumentException("Invalid reservation ID: " + report.getReservationId());
        }
        reportService.saveReport(report);
    }

    @GetMapping("/reservation/{reservationId}")
    public List<Report> getReportsByReservationId(@PathVariable Long reservationId) {
        return reportService.getReportsByReservationId(reservationId);
    }
}