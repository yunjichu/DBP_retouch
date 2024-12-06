package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ReservationRepository  {

    // 기존 기능: 특정 시설 ID와 날짜를 기준으로 예약 수를 세는 메서드
    int countByFacility_FacilityIdAndReservationDateAndStartTime(Long facilityId, LocalDate date, Integer timeSlot);

    // 기존 기능: 특정 학생 ID를 기준으로 예약 목록을 조회하는 메서드
    List<Reservation> findByStudentId(Long studentId);

    // 기존 기능: 특정 위치에 대한 예약 수를 카운트하는 메서드
    int countByFacility_Location(String location);

    // 기존 기능: 시간 범위 내 예약을 찾는 메서드
    Optional<String> checkFacilityAvailability(String facilityName, LocalDate date, int startTime, int endTime);
}
