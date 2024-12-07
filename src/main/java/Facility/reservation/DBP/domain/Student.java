package Facility.reservation.DBP.domain;


import jakarta.persistence.*;

@Entity
@Table(name = "STUDENT")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_ID")
    private Long studentId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;

    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
