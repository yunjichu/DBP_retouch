package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Facility;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcFacilityRepository implements FacilityRepository{

    private final DataSource dataSource;

    public JdbcFacilityRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 시설 이름으로 시설 정보를 조회합니다.
     *
     * @param facilityName 조회할 시설 이름
     * @return 조회된 Facility 객체
     */
    @Override
    public Facility findByName(String facilityName) {
        String sql = "SELECT * FROM FACILITY WHERE FACILITY_NAME = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, facilityName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapFacility(rs);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to fetch facility by name", e);
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 모든 시설 정보를 조회합니다.
     *
     * @return Facility 객체 리스트
     */
    @Override
    public List<Facility> findAll() {
        String sql = "SELECT * FROM FACILITY";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Facility> facilities = new ArrayList<>();
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                facilities.add(mapFacility(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to fetch all facilities", e);
        } finally {
            close(conn, pstmt, rs);
        }
        return facilities;
    }

    /**
     * 특정 시간대에 시설이 예약 가능한지 확인합니다.
     *
     * @param facilityName 확인할 시설 이름
     * @param timeSlot 확인할 시간대
     * @return 예약 가능 여부 (true/false)
     */
    @Override
    public boolean isAvailable(String facilityName, Integer timeSlot) {
        String sql = "SELECT COUNT(*) FROM FACILITY WHERE FACILITY_NAME = ? AND AVAILABLE_START_TIME <= ? AND AVAILABLE_END_TIME >= ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, facilityName);
            pstmt.setInt(2, timeSlot);
            pstmt.setInt(3, timeSlot);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to check facility availability", e);
        } finally {
            close(conn, pstmt, rs);
        }
        return false;
    }
    /**
     * ResultSet 데이터를 Facility 객체로 매핑합니다.
     */
    private Facility mapFacility(ResultSet rs) throws SQLException {
        Facility facility = new Facility();
        facility.setFacilityId(rs.getLong("FACILITY_ID"));
        facility.setFacilityName(rs.getString("FACILITY_NAME"));
        facility.setCapacity(rs.getInt("CAPACITY"));
        facility.setLocation(rs.getString("LOCATION"));
        facility.setUsageGuidelines(rs.getString("USAGE_GUIDELINES"));
        facility.setAvailableStartTime(rs.getInt("AVAILABLE_START_TIME"));
        facility.setAvailableEndTime(rs.getInt("AVAILABLE_END_TIME"));
        facility.setDepartmentId(rs.getLong("DEPARTMENT_ID"));
        facility.setTotalReservations(rs.getInt("TOTAL_RESERVATIONS"));
        return facility;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

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

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
