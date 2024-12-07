package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Reservation;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Repository
public class JdbcReservationRepository implements ReservationRepository{

    private final DataSource dataSource;

    public JdbcReservationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 예약 데이터 저장
     * - STUDENT_ID, FACILITY_ID, RESERVATION_DATE, RESERVATION_TIME을 데이터베이스에 삽입.
     */
    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO RESERVATION (STUDENT_ID, FACILITY_ID, RESERVATION_DATE, RESERVATION_TIME) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, reservation.getStudentId());
            pstmt.setLong(2, reservation.getFacilityId());
            pstmt.setObject(3, reservation.getReservationDate());
            pstmt.setObject(4, reservation.getReservationTime());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to save the reservation");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setReservationId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Failed to retrieve reservation ID");
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error saving reservation", e);
        } finally {
            close(conn, pstmt, null);
        }
        return reservation;
    }

    /**
     * 예약 데이터 삭제
     * - 예약 ID를 기반으로 데이터 삭제.
     */
    @Override
    public void deleteById(Long reservationId) {
        String sql = "DELETE FROM RESERVATION WHERE RESERVATION_ID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, reservationId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error deleting reservation", e);
        } finally {
            close(conn, pstmt, null);
        }
    }

    /**
     * 예약 데이터 수정
     * - 예약 ID를 기반으로 STUDENT_ID, FACILITY_ID, RESERVATION_DATE, RESERVATION_TIME 수정.
     */
    @Override
    public Reservation update(Long reservationId, Reservation reservation) {
        String sql = "UPDATE RESERVATION SET STUDENT_ID = ?, FACILITY_ID = ?, RESERVATION_DATE = ?, RESERVATION_TIME = ? WHERE RESERVATION_ID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, reservation.getStudentId());
            pstmt.setLong(2, reservation.getFacilityId());
            pstmt.setObject(3, reservation.getReservationDate());
            pstmt.setObject(4, reservation.getReservationTime());
            pstmt.setLong(5, reservationId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to update the reservation");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error updating reservation", e);
        } finally {
            close(conn, pstmt, null);
        }
        return reservation;
    }

    /**
     * 모든 예약 데이터 조회
     * - 데이터베이스에서 모든 예약 데이터를 가져와 리스트로 반환.
     */
    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM RESERVATION";
        List<Reservation> reservations = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                reservations.add(mapRowToReservation(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error retrieving reservations", e);
        } finally {
            close(conn, pstmt, rs);
        }
        return reservations;
    }

    /**
     * 특정 학생 ID로 예약 데이터 조회
     * - STUDENT_ID를 조건으로 예약 데이터 조회.
     */
    @Override
    public List<Reservation> findByStudentId(Long studentId) {
        String sql = "SELECT * FROM RESERVATION WHERE STUDENT_ID = ?";
        List<Reservation> reservations = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, studentId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                reservations.add(mapRowToReservation(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error retrieving reservations by student ID", e);
        } finally {
            close(conn, pstmt, rs);
        }
        return reservations;
    }

    /**
     * 특정 시설, 날짜, 시간대 예약 상태 확인
     * - 조건에 맞는 예약이 존재하면 true 반환.
     */
    @Override
    public boolean existsByFacilityIdAndReservationDateAndReservationTime(Long facilityId, LocalDate reservationDate, LocalTime reservationTime) {
        String sql = "SELECT COUNT(*) FROM RESERVATION WHERE FACILITY_ID = ? AND RESERVATION_DATE = ? AND RESERVATION_TIME = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, facilityId);
            pstmt.setObject(2, reservationDate);
            pstmt.setObject(3, reservationTime);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error checking reservation existence", e);
        } finally {
            close(conn, pstmt, rs);
        }
        return false;
    }

    /**
     * 예약 ID로 예약 상태 확인
     * - 예약 ID가 존재하면 true 반환.
     */
    @Override
    public boolean existsById(Long reservationId) {
        String sql = "SELECT COUNT(*) FROM RESERVATION WHERE RESERVATION_ID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, reservationId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error checking reservation by ID", e);
        } finally {
            close(conn, pstmt, rs);
        }
        return false;
    }

    /**
     * ResultSet 데이터를 Reservation 객체로 변환
     * - 데이터베이스에서 조회한 결과를 Reservation 객체로 매핑.
     */
    private Reservation mapRowToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(rs.getLong("RESERVATION_ID"));
        reservation.setStudentId(rs.getLong("STUDENT_ID"));
        reservation.setFacilityId(rs.getLong("FACILITY_ID"));
        reservation.setReservationDate(rs.getDate("RESERVATION_DATE").toLocalDate());
        reservation.setReservationTime(rs.getTime("RESERVATION_TIME").toLocalTime());
        return reservation;
    }

    /**
     * 데이터베이스 Connection을 가져옵니다.
     * - Spring의 DataSourceUtils를 사용하여 연결을 관리.
     */
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    /**
     * 데이터베이스 리소스(Connection, PreparedStatement, ResultSet)를 안전하게 닫습니다.
     * - 자원 누수를 방지.
     */
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) DataSourceUtils.releaseConnection(conn, dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
