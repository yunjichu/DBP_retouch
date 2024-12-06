package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ReportRepository{

    List<Report> findByReservation_StudentId(Long studentId);

    List<Report> findByReservation_ReservationId(String reservationId);
}

