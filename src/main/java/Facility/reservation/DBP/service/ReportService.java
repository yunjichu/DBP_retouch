
package Facility.reservation.DBP.service;

import Facility.reservation.DBP.domain.Report;
import Facility.reservation.DBP.reporsitory.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;


    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // 신고 내용 추가
    public Report createReport(String reservationId, String reportContent, String processingStatus) {
        Report report = new Report();
        report.setReservationId(reservationId); // String 타입으로 저장
        report.setReportContent(reportContent);
        report.setProcessingStatus(processingStatus);

        return reportRepository.save(report);
    }


    // 신고 목록 조회
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }




    // 예약번호로 신고 조회
    public List<Report> getReportsByReservationId(String reservationId) {
        // reservationId로 Report를 찾음
        List<Report> reports = reportRepository.findByReservation_ReservationId(reservationId);

        // 각 Report에서 Reservation을 통해 studentId를 찾아냄
        for (Report report : reports) {
            Long studentId = report.getReservation().getStudentId();  // reservationId로 연결된 Reservation의 studentId를 조회
            System.out.println("학생 ID: " + studentId); // 학생 ID 확인
        }
        return reports;
    }


    public List<Report> getReportsByStudentId(Long studentId) {
        return reportRepository.findByReservation_StudentId(studentId);
    }


}