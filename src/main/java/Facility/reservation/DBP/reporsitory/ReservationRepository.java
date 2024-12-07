package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ReservationRepository  {

    // 예약 생성
    Reservation save(Reservation reservation);

    // 예약 삭제
    void deleteById(String resevasionid);

    // 예약 수정 (Update)
    Reservation update(Long id, Reservation updatedReservation);

    // 특정 학생의 예약 목록 조회
    List<Reservation> findAllByStudentId(String studentId);

    // 시설의 예약 가능한 시간대 조회
    List<Reservation> findAvailableReservations(String facilityName, LocalDate date);

    // 특정 예약 조회 (Optional)
    Optional<Reservation> findByFacilityId(String id);

    // 기존 기능: 특정 위치에 대한 예약 수를 카운트하는 메서드
    int countByFacility_Location(String location);

    // 기존 기능: 시간 범위 내 예약을 찾는 메서드
    Optional<String> checkFacilityAvailability(String facilityName, LocalDate date, int startTime, int endTime);
}
