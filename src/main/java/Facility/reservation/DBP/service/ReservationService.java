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

    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByStudentId(Long studentId) {
        return reservationRepository.findByStudentId(studentId);
    }

    public boolean isReservationAvailable(Long facilityId, LocalDate reservationDate, LocalTime reservationTime) {
        return !reservationRepository.existsByFacilityIdAndReservationDateAndReservationTime(facilityId, reservationDate, reservationTime);
    }

    public boolean existsById(Long reservationId) {
        return reservationRepository.existsById(reservationId);
    }

}


