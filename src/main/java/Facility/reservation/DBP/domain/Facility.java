package Facility.reservation.DBP.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "FACILITY")
public class Facility {
    private Long facilityId;
    private String facilityName;
    private Integer capacity;
    private String location;
    private String usageGuidelines;
    private Integer availableStartTime;
    private Integer availableEndTime;
    private Integer reservationCount;
    private boolean available;

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsageGuidelines() {
        return usageGuidelines;
    }

    public void setUsageGuidelines(String usageGuidelines) {
        this.usageGuidelines = usageGuidelines;
    }

    public Integer getAvailableStartTime() {
        return availableStartTime;
    }

    public void setAvailableStartTime(Integer availableStartTime) {
        this.availableStartTime = availableStartTime;
    }

    public Integer getAvailableEndTime() {
        return availableEndTime;
    }

    public void setAvailableEndTime(Integer availableEndTime) {
        this.availableEndTime = availableEndTime;
    }

    public Integer getReservationCount() {
        return reservationCount;
    }

    public void setReservationCount(Integer reservationCount) {
        this.reservationCount = reservationCount;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
