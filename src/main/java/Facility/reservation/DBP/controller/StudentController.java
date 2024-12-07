package Facility.reservation.DBP.controller;

import Facility.reservation.DBP.domain.Student;
import Facility.reservation.DBP.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{studentId}")
    public Student getStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }
}

