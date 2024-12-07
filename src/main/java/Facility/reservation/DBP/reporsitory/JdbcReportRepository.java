package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Report;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcReportRepository implements ReportRepository{

    private final DataSource dataSource;

    public JdbcReportRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 신고 데이터를 저장합니다.
     * @param report 저장할 신고 데이터
     */
    @Override
    public void save(Report report) {
        String sql = "INSERT INTO REPORT (RESERVATION_ID, REPORT_CONTENT, PROCESSING_STATUS) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, report.getReservationId());
            pstmt.setString(2, report.getReportContent());
            pstmt.setString(3, report.getProcessingStatus());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to save the report");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    report.setReportId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Failed to retrieve report ID");
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error saving report", e);
        } finally {
            close(conn, pstmt, null);
        }
    }

    /**
     * 예약 ID로 신고 데이터를 조회합니다.
     * @param reservationId 조회할 예약 ID
     * @return 신고 데이터 리스트
     */
    @Override
    public List<Report> findByReservationId(Long reservationId) {
        String sql = "SELECT * FROM REPORT WHERE RESERVATION_ID = ?";
        List<Report> reports = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, reservationId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                reports.add(mapRowToReport(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error retrieving reports by reservation ID", e);
        } finally {
            close(conn, pstmt, rs);
        }
        return reports;
    }

    /**
     * ResultSet 데이터를 Report 객체로 변환합니다.
     * @param rs 변환할 ResultSet
     * @return 변환된 Report 객체
     */
    private Report mapRowToReport(ResultSet rs) throws SQLException {
        Report report = new Report();
        report.setReportId(rs.getLong("REPORT_ID"));
        report.setReservationId(rs.getLong("RESERVATION_ID"));
        report.setReportContent(rs.getString("REPORT_CONTENT"));
        report.setProcessingStatus(rs.getString("PROCESSING_STATUS"));
        return report;
    }

    /**
     * 데이터베이스 Connection을 가져옵니다.
     * @return Connection 객체
     */
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    /**
     * 데이터베이스 리소스를 안전하게 닫습니다.
     * @param conn Connection 객체
     * @param pstmt PreparedStatement 객체
     * @param rs ResultSet 객체
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
