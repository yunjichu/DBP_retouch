package Facility.reservation.DBP.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "STUDENT")
@Getter
@Setter
public class Student {

    @Id
    @Column(name = "STUDENT_ID", nullable = false)
    private Long studentId; // @GeneratedValue 제거

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "GENDER", nullable = true, length = 2) // nullable 추가 명시
    private String gender;

    @Column(name = "CONTACT_NUMBER", length = 20)
    private String contactNumber;

    @Column(name = "DEPARTMENT_ID")
    private Long departmentId; // ManyToOne 매핑 제거 및 단순 컬럼 처리
}
