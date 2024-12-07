package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Student;

public interface StudentRepository {
    // 학번으로 학생 조회
    Student findByStudentId(Long studentId);
}



