package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface ReservationRepository {

    // 예약 삽입
    Reservation save(Reservation reservation);

    // 예약 삭제
    void deleteById(Long reservationId);

    // 예약 변경
    Reservation update(Long reservationId, Reservation reservation);

    // 예약 조회
    List<Reservation> findAll();

    // 학번으로 예약 현황 조회
    List<Reservation> findByStudentId(Long studentId);

    // 특정 시설, 날짜, 시간대의 예약 상태 확인
    boolean existsByFacilityIdAndReservationDateAndReservationTime(Long facilityId, LocalDate reservationDate, LocalTime reservationTime);

    //
    boolean existsById(Long reservationId);
}
