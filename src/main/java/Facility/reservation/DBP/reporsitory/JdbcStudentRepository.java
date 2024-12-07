package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Student;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class JdbcStudentRepository implements StudentRepository{

    private final DataSource dataSource;

    public JdbcStudentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 학생 ID로 학생 정보를 조회합니다.
     *
     * @param studentId 조회할 학생의 ID
     * @return Optional로 감싼 Student 객체
     */
    @Override
    public Optional<Student> findByStudentId(Long studentId) {
        String sql = "SELECT * FROM STUDENT WHERE STUDENT_ID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, studentId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getLong("STUDENT_ID"));
                student.setName(rs.getString("NAME"));
                student.setGender(rs.getString("GENDER"));
                student.setContactNumber(rs.getString("CONTACT_NUMBER"));
                student.setDepartmentId(rs.getLong("DEPARTMENT_ID"));
                return Optional.of(student);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to fetch student by ID", e);
        } finally {
            close(conn, pstmt, rs);
        }
        return Optional.empty();
    }


    /**
     * DataSource를 통해 데이터베이스 연결을 가져옵니다.
     */
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    /**
     * 데이터베이스 리소스를 해제합니다 (Connection, PreparedStatement, ResultSet 순).
     *
     * @param conn 데이터베이스 연결 객체
     * @param pstmt PreparedStatement 객체
     * @param rs ResultSet 객체
     */
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * DataSourceUtils를 사용하여 Connection을 반환합니다.
     */
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
