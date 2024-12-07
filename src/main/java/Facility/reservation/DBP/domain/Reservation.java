package Facility.reservation.DBP.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private Long reservationId;

    @Column(name = "FACILITY_ID", nullable = false)
    private Long facilityId;

    @Column(name = "STUDENT_ID", nullable = false)
    private Long studentId;

    @Column(name = "RESERVATION_DATE", nullable = false)
    private LocalTime reservationDate;

    @Column(name = "RESERVATION_TIME", nullable = false)
    private LocalTime reservationTime;

    // Getters and Setters
    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalTime reservationTime) {
        this.reservationTime = reservationTime;
    }
}