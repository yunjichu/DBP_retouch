package Facility.reservation.DBP.domain;

import jakarta.persistence.*;


@Entity
@Table(name = "FACILITY")
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FACILITY_ID")
    private Long facilityId;

    @Column(name = "FACILITY_NAME", nullable = false)
    private String facilityName;

    @Column(name = "CAPACITY")
    private Integer capacity;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "USAGE_GUIDELINES")
    private String usageGuidelines;

    @Column(name = "AVAILABLE_START_TIME")
    private Integer availableStartTime;

    @Column(name = "AVAILABLE_END_TIME")
    private Integer availableEndTime;

    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;

    @Column(name = "TOTAL_RESERVATIONS")
    private Integer totalReservations;

    // Getters and Setters
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getTotalReservations() {
        return totalReservations;
    }

    public void setTotalReservations(Integer totalReservations) {
        this.totalReservations = totalReservations;
    }
}