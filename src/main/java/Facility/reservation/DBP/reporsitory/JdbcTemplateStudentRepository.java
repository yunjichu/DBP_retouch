package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateStudentRepository implements StudentRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateStudentRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 학생 ID로 학생 정보를 조회합니다.
     *
     * @param studentId 조회할 학생의 ID
     * @return Optional로 감싼 Student 객체
     */
    @Override
    public Optional<Student> findByStudentId(Long studentId) {
        List<Student> result = jdbcTemplate.query("SELECT * FROM STUDENT WHERE STUDENT_ID = ?",
                studentRowMapper(), studentId);
        return result.stream().findAny();
    }

    /**
     * RowMapper를 사용하여 ResultSet 데이터를 Student 객체로 매핑합니다.
     */
    private RowMapper<Student> studentRowMapper() {
        return (rs, rowNum) -> {
            Student student = new Student();
            student.setStudentId(rs.getLong("STUDENT_ID"));
            student.setName(rs.getString("NAME"));
            student.setGender(rs.getString("GENDER"));
            student.setContactNumber(rs.getString("CONTACT_NUMBER"));
            student.setDepartmentId(rs.getLong("DEPARTMENT_ID"));
            return student;
        };
    }
}
