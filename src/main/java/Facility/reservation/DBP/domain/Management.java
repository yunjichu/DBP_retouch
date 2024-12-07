package Facility.reservation.DBP.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "MANAGEMENT")
public class Management {

    @Column(name = "FACILITY_ID", nullable = false)
    private Long facilityId;

    @Column(name = "MANAGER", nullable = false)
    private String manager;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    // Getters and Setters
    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
