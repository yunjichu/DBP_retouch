package Facility.reservation.DBP.controller;

import Facility.reservation.DBP.domain.Reservation;
import Facility.reservation.DBP.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * 예약 저장 API
     * - HTTP POST 요청을 처리하여 예약 데이터를 저장.
     */
    @PostMapping
    public ResponseEntity<Reservation> saveReservation(@RequestBody Reservation reservation) {
        Reservation savedReservation = reservationService.saveReservation(reservation);
        return ResponseEntity.ok(savedReservation);
    }

    /**
     * 예약 변경 API
     * - HTTP PUT 요청을 처리하여 예약 데이터를 수정.
     */
    @PutMapping("/{reservationId}")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable Long reservationId,
            @RequestBody Reservation updatedReservation) {
        Reservation updated = reservationService.updateReservation(reservationId, updatedReservation);
        return ResponseEntity.ok(updated);
    }

    /**
     * 예약 삭제 API
     * - HTTP DELETE 요청을 처리하여 특정 예약을 삭제.
     */
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 모든 예약 조회 API
     * - HTTP GET 요청을 처리하여 모든 예약 데이터를 반환.
     */
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.findAllReservations();
        return ResponseEntity.ok(reservations);
    }

    /**
     * 특정 학생 ID로 예약 조회 API
     * - HTTP GET 요청을 처리하여 특정 학생의 예약 데이터를 반환.
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Reservation>> getReservationsByStudentId(@PathVariable Long studentId) {
        List<Reservation> reservations = reservationService.findReservationsByStudentId(studentId);
        return ResponseEntity.ok(reservations);
    }
}

