package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplatesReservationRepository implements ReservationRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplatesReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 예약 데이터 저장
     * - SimpleJdbcInsert를 사용하여 데이터 삽입.
     */
    @Override
    public Reservation save(Reservation reservation) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("RESERVATION").usingGeneratedKeyColumns("RESERVATION_ID");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("STUDENT_ID", reservation.getStudentId());
        parameters.put("FACILITY_ID", reservation.getFacilityId());
        parameters.put("RESERVATION_DATE", reservation.getReservationDate());
        parameters.put("RESERVATION_TIME", reservation.getReservationTime());

        Number key = jdbcInsert.executeAndReturnKey(parameters);
        reservation.setReservationId(key.longValue());
        return reservation;
    }

    /**
     * 예약 데이터 삭제
     * - 예약 ID를 기반으로 데이터 삭제.
     */
    @Override
    public void deleteById(Long reservationId) {
        String sql = "DELETE FROM RESERVATION WHERE RESERVATION_ID = ?";
        jdbcTemplate.update(sql, reservationId);
    }

    /**
     * 예약 데이터 수정
     * - 예약 ID를 기반으로 데이터 업데이트.
     */
    @Override
    public Reservation update(Long reservationId, Reservation reservation) {
        String sql = "UPDATE RESERVATION SET STUDENT_ID = ?, FACILITY_ID = ?, RESERVATION_DATE = ?, RESERVATION_TIME = ? WHERE RESERVATION_ID = ?";
        jdbcTemplate.update(sql,
                reservation.getStudentId(),
                reservation.getFacilityId(),
                reservation.getReservationDate(),
                reservation.getReservationTime(),
                reservationId);
        reservation.setReservationId(reservationId);
        return reservation;
    }

    /**
     * 모든 예약 데이터 조회
     * - 데이터베이스에서 모든 예약 데이터를 가져와 리스트로 반환.
     */
    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM RESERVATION";
        return jdbcTemplate.query(sql, reservationRowMapper());
    }

    /**
     * 특정 학생 ID로 예약 데이터 조회
     * - STUDENT_ID를 조건으로 예약 데이터 조회.
     */
    @Override
    public List<Reservation> findByStudentId(Long studentId) {
        String sql = "SELECT * FROM RESERVATION WHERE STUDENT_ID = ?";
        return jdbcTemplate.query(sql, reservationRowMapper(), studentId);
    }

    /**
     * 특정 시설, 날짜, 시간대 예약 상태 확인
     * - 조건에 맞는 예약이 존재하면 true 반환.
     */
    @Override
    public boolean existsByFacilityIdAndReservationDateAndReservationTime(Long facilityId, LocalDate reservationDate, LocalTime reservationTime) {
        String sql = "SELECT COUNT(*) FROM RESERVATION WHERE FACILITY_ID = ? AND RESERVATION_DATE = ? AND RESERVATION_TIME = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, facilityId, reservationDate, reservationTime);
        return count != null && count > 0;
    }

    /**
     * 예약 ID로 예약 상태 확인
     * - 예약 ID가 존재하면 true 반환.
     */
    @Override
    public boolean existsById(Long reservationId) {
        String sql = "SELECT COUNT(*) FROM RESERVATION WHERE RESERVATION_ID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, reservationId);
        return count != null && count > 0;
    }

    /**
     * ResultSet 데이터를 Reservation 객체로 매핑
     * - RowMapper를 사용하여 데이터베이스에서 조회한 결과를 Reservation 객체로 변환.
     */
    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rowNum) -> {
            Reservation reservation = new Reservation();
            reservation.setReservationId(rs.getLong("RESERVATION_ID"));
            reservation.setStudentId(rs.getLong("STUDENT_ID"));
            reservation.setFacilityId(rs.getLong("FACILITY_ID"));
            reservation.setReservationDate(rs.getDate("RESERVATION_DATE").toLocalDate());
            reservation.setReservationTime(rs.getTime("RESERVATION_TIME").toLocalTime());
            return reservation;
        };
    }
}
