package Facility.reservation.DBP.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "REPORT")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Long reportId;

    @Column(name = "RESERVATION_ID", nullable = false)
    private Long reservationId;

    @Column(name = "REPORT_CONTENT", nullable = false)
    private String reportContent;

    @Column(name = "PROCESSING_STATUS")
    private String processingStatus;

    // Getters and Setters
    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public String getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(String processingStatus) {
        this.processingStatus = processingStatus;
    }
}