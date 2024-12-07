package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Reservation;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcReservationRepository1 implements ReservationRepository{
    private final DataSource dataSource;

    public JdbcReservationRepository1(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //예약 생성
    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO 예약내역 (학번, 시설번호, 예약번호, 예약날짜, 예약시간) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, reservation.getStudentId());
            pstmt.setLong(2, reservation.getFacilityId());
            pstmt.setString(3, reservation.getReservationId());
            pstmt.setDate(4, Date.valueOf(reservation.getReservationDate()));
            pstmt.setInt(5, reservation.getStartTime());
            pstmt.setInt(6, reservation.getEndTime());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }

    //예약 삭제
    @Override
    public void deleteById(String reservationId) {
        String sql = "DELETE FROM 예약내역 WHERE 예약번호 = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, reservationId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //예약 수정
    @Override
    public Reservation update(String reservationId, Reservation reservation) {
        String sql = "UPDATE 예약내역 SET 학번 = ?, 시설번호 = ?, 예약날짜 = ?, 예약시간 = ? WHERE 예약번호 = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, reservation.getStudentId());
            pstmt.setLong(2, reservation.getFacilityId());
            pstmt.setString(3, reservation.getReservationId());
            pstmt.setDate(4, Date.valueOf(reservation.getReservationDate()));
            pstmt.setInt(5, reservation.getStartTime());
            pstmt.setInt(6, reservation.getEndTime());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }

    @Override
    public boolean isAvailable(String facilityName, LocalDate date, int timeSlot) {
        String sql = "SELECT COUNT(*) FROM 예약내역 r JOIN 시설 f ON r.시설번호 = f.시설번호 " +
                "WHERE f.시설이름 = ? AND r.예약날짜 = ? AND r.예약시간 = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, facilityName);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.setInt(3, timeSlot);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<Reservation> findAllByStudentId(String studentId) {
        String sql = "SELECT * FROM 예약내역 WHERE 학번 = ?";
        List<Reservation> reservations = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    reservations.add(new Reservation(
                            rs.getString("예약번호"),
                            rs.getString("학번"),
                            rs.getInt("시설번호"),
                            rs.getDate("예약날짜").toLocalDate(),
                            rs.getInt("예약시간")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }

}
