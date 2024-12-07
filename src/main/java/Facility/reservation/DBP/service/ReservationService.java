package Facility.reservation.DBP.service;

import Facility.reservation.DBP.domain.Reservation;
import Facility.reservation.DBP.domain.Facility;
import Facility.reservation.DBP.reporsitory.FacilityRepository;
import Facility.reservation.DBP.reporsitory.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    // Facility ID로 시설 조회
    public Facility getFacilityById(Long facilityId) {
        return facilityRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Facility ID를 가진 시설이 존재하지 않습니다: " + facilityId));
    }

    // 모든 시설 조회
    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    // 학번을 기준으로 예약 조회
    public List<Reservation> getReservationsByStudentId(Long studentId) {
        return reservationRepository.findByStudentId(studentId);
    }

    /*
     * 특정 날짜와 시설의 예약 개수 조회
     * @param facilityId 시설 ID
     * @param date       예약 날짜
     * @param timeSlot   예약 시간대
     * @return 해당 시설과 날짜에 해당 시간대의 예약 개수
     */

    public int getCountByFacilityAndDate(Long facilityId, LocalDate date, Integer timeSlot) {
        return reservationRepository.countByFacility_FacilityIdAndReservationDateAndStartTime(facilityId, date, timeSlot);
    }

    // 예약 추가
    public void addReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    // 예약 수정
    public void updateReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    // 예약 삭제
    public void deleteReservation(String reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    // 예약 ID로 예약 조회
    public Reservation getReservationById(String reservationId) {
        return reservationRepository.findByFacilityId(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다: " + reservationId));
    }

    /**
     * location을 기준으로 예약 횟수 반환
     *
     * @param location 시설 위치
     * @return 예약 횟수
     */
    public int getCountByLocation(String location) {
        return reservationRepository.countByFacility_Location(location);
    }

    /**
     * 시설 예약 가능 여부 확인
     *
     * @param facilityId 시설 ID
     * @param date       예약 날짜
     * @param timeSlot   예약 시간대
     * @return 예약 가능 여부 (true: 가능, false: 불가능)
     */
    public boolean isFacilityAvailable(Long facilityId, LocalDate date, Integer timeSlot) {
        int reservationCount = reservationRepository.countByFacility_FacilityIdAndReservationDateAndStartTime(facilityId, date, timeSlot);
        return reservationCount == 0; // 예약이 없으면 true
    }

    /**
     * 시설 예약 가능 여부 확인 (시간 범위 포함)
     *
     * @param facilityId 시설 ID
     * @param date       예약 날짜
     * @param startTime  시작 시간
     * @param endTime    종료 시간
     * @return 예약 가능 여부 (true: 가능, false: 불가능)
     */
    public boolean isFacilityAvailableWithinTime(Long facilityId, LocalDate date, Integer startTime, Integer endTime) {
        List<Reservation> conflictingReservations = reservationRepository.findReservationsWithinTimeRange(facilityId, date, startTime, endTime);
        return conflictingReservations.isEmpty(); // 충돌하는 예약이 없으면 true
    }
}
