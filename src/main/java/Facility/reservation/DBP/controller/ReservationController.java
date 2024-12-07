package Facility.reservation.DBP.controller;

import Facility.reservation.DBP.domain.Reservation;
import Facility.reservation.DBP.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/add")
    public void addReservation(@RequestBody Reservation reservation) {
        reservationService.saveReservation(reservation);
    }

    @DeleteMapping("/delete/{reservationId}")
    public void deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/student/{studentId}")
    public List<Reservation> getReservationsByStudentId(@PathVariable Long studentId) {
        return reservationService.getReservationsByStudentId(studentId);
    }

    @GetMapping("/availability")
    public boolean checkReservationAvailability(
            @RequestParam Long facilityId,
            @RequestParam LocalDate reservationDate,
            @RequestParam LocalTime reservationTime) {
        return reservationService.isReservationAvailable(facilityId, reservationDate, reservationTime);
    }
}

