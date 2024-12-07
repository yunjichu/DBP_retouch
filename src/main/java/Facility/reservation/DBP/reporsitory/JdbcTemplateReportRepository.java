package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Report;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateReportRepository implements ReportRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReportRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 신고 데이터를 저장합니다.
     * @param report 저장할 신고 데이터
     */
    @Override
    public void save(Report report) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("REPORT").usingGeneratedKeyColumns("REPORT_ID");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("RESERVATION_ID", report.getReservationId());
        parameters.put("REPORT_CONTENT", report.getReportContent());
        parameters.put("PROCESSING_STATUS", report.getProcessingStatus());

        Number key = jdbcInsert.executeAndReturnKey(parameters);
        report.setReportId(key.longValue());
    }

    /**
     * 예약 ID로 신고 데이터를 조회합니다.
     * @param reservationId 조회할 예약 ID
     * @return 신고 데이터 리스트
     */
    @Override
    public List<Report> findByReservationId(Long reservationId) {
        String sql = "SELECT * FROM REPORT WHERE RESERVATION_ID = ?";
        return jdbcTemplate.query(sql, reportRowMapper(), reservationId);
    }

    /**
     * ResultSet 데이터를 Report 객체로 매핑합니다.
     * @return Report 객체로 변환하는 RowMapper
     */
    private RowMapper<Report> reportRowMapper() {
        return (rs, rowNum) -> {
            Report report = new Report();
            report.setReportId(rs.getLong("REPORT_ID"));
            report.setReservationId(rs.getLong("RESERVATION_ID"));
            report.setReportContent(rs.getString("REPORT_CONTENT"));
            report.setProcessingStatus(rs.getString("PROCESSING_STATUS"));
            return report;
        };
    }
}
