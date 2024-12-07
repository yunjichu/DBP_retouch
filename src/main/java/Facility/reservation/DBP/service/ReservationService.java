package Facility.reservation.DBP.service;

import Facility.reservation.DBP.domain.Reservation;
import Facility.reservation.DBP.reporsitory.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * 예약 저장 서비스 메서드
     * - 예약 데이터를 데이터베이스에 저장.
     */
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    /**
     * 예약 변경 서비스 메서드
     * - 예약 ID로 기존 예약 데이터를 수정.
     */
    public Reservation updateReservation(Long reservationId, Reservation updatedReservation) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new IllegalArgumentException("Reservation ID not found: " + reservationId);
        }
        return reservationRepository.update(reservationId, updatedReservation);
    }

    /**
     * 예약 삭제 서비스 메서드
     * - 예약 ID로 데이터 삭제.
     */
    public void deleteReservation(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new IllegalArgumentException("Reservation ID not found: " + reservationId);
        }
        reservationRepository.deleteById(reservationId);
    }

    /**
     * 모든 예약 조회 서비스 메서드
     */
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * 특정 학생 ID로 예약 조회 서비스 메서드
     */
    public List<Reservation> findReservationsByStudentId(Long studentId) {
        return reservationRepository.findByStudentId(studentId);
    }

    /**
     * 예약 ID 존재 여부 확인 메서드
     * - 예약 ID가 존재하면 true 반환, 그렇지 않으면 false 반환.
     */
    public boolean existsById(Long reservationId) {
        return reservationRepository.existsById(reservationId);
    }

}


