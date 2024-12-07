package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Student;

import java.util.Optional;

public interface StudentRepository {
    // 학번으로 학생 조회
    Optional<Student> findByStudentId(Long studentId);
}



