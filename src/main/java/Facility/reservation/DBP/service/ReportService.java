
package Facility.reservation.DBP.service;

import Facility.reservation.DBP.domain.Report;
import Facility.reservation.DBP.reporsitory.ReportRepository;
import Facility.reservation.DBP.service.ReservationService;

import java.util.List;

public class ReportService {

    private final ReservationService reservationService;
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository, ReservationService reservationService) {
        this.reportRepository = reportRepository;
        this.reservationService = reservationService;
    }

    public void saveReport(Report report) {
        reportRepository.save(report);
    }

    public List<Report> getReportsByReservationId(Long reservationId) {
        return reportRepository.findByReservationId(reservationId);
    }
}