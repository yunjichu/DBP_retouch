package Facility.reservation.DBP.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "DEPARTMENT")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;

    @Column(name = "DEPARTMENT_NAME", nullable = false)
    private String departmentName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "OFFICE")
    private String office;

    // Getters and Setters
    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}