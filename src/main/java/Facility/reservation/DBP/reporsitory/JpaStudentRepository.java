package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Student;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaStudentRepository implements StudentRepository{

    private final EntityManager em;

    public JpaStudentRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * 학생 ID로 학생 정보를 조회합니다.
     *
     * @param studentId 조회할 학생의 ID
     * @return Optional로 감싼 Student 객체
     */
    @Override
    public Optional<Student> findByStudentId(Long studentId) {
        Student student = em.find(Student.class, studentId);
        return Optional.ofNullable(student);
    }
}
