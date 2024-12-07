package Facility.reservation.DBP.service;

import Facility.reservation.DBP.domain.Student;
import Facility.reservation.DBP.reporsitory.StudentRepository;

public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.findByStudentId(studentId);
    }
}
