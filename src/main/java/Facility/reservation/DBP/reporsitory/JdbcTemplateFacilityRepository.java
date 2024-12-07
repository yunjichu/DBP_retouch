package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Facility;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class JdbcTemplateFacilityRepository implements FacilityRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateFacilityRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
        return jdbcTemplate.queryForObject(sql, facilityRowMapper(), facilityName);
    }

    /**
     * 모든 시설 정보를 조회합니다.
     *
     * @return Facility 객체 리스트
     */
    @Override
    public List<Facility> findAll() {
        String sql = "SELECT * FROM FACILITY";
        return jdbcTemplate.query(sql, facilityRowMapper());
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
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, facilityName, timeSlot, timeSlot);
        return count != null && count > 0;
    }

    /**
     * ResultSet 데이터를 Facility 객체로 매핑합니다.
     *
     * @return Facility 객체로 변환하는 RowMapper
     */
    private RowMapper<Facility> facilityRowMapper() {
        return (rs, rowNum) -> {
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
        };
    }
}
