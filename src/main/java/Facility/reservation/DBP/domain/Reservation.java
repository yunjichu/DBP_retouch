package Facility.reservation.DBP.domain;

import java.time.LocalDate;

public class Reservation {
    private String reservationId; // 예약 ID (Primary Key)
    private Long studentId; // 학생 ID
    private Facility facility; // 시설과의 Many-to-One 관계
    private LocalDate reservationDate; // 예약 날짜
    private Integer startTime; // 시작 시간
    private Integer endTime; // 종료 시간
    private String formattedDate; // 포맷된 날짜 (DB에 저장하지 않음)

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }
}
