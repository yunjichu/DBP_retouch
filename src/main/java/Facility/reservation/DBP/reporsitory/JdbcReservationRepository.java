package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Reservation;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static java.sql.DriverManager.getConnection;
import static jdk.internal.net.http.common.Utils.close;

public class JdbcReservationRepository implements  ReservationRepository{

    private final DataSource dataSource;

    public JdbcReservationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Reservation save(Reservation resevation) {
            String sql = "insert into reservation(id, name) values(?, ?)";
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                conn = getConnection();  // private method
                pstmt = conn.prepareStatement(sql);
                pstmt.setLong(1, resevation.getReservationId());
                pstmt.setLong(2, resevation.getStudentId());
                pstmt.setLong(3,resevation.getFacilityId());
                int num = pstmt.executeUpdate();
                if (num == 1) {
                    return resevation;
                } else {
                    throw new SQLException("id 조회 실패");
                }
            } catch (Exception e) {
                throw new IllegalStateException(e);
            } finally {
                close(conn, pstmt, rs);
            }
    }

    @Override
    public void deleteById(String reservationId) {

    }

    @Override
    public Reservation update(String reservationId, Reservation updatedReservation) {
        return null;
    }

    @Override
    public boolean isAvailable(String facilityName, LocalDate date, int timeSlot) {
        return false;
    }

    @Override
    public List<Reservation> findAllByStudentId(String studentId) {
        return List.of();
    }
}
