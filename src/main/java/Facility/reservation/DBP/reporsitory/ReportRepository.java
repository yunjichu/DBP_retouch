package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ReportRepository{

    // 신고 생성
    Report save(Report report);

    // 특정 학생의 신고 목록 조회
    List<Report> findAllByStudentId(String studentId);

    // 신고 번호로 신고 조회 (Optional)
    Optional<Report> findById(Long id);

    // 특정 예약 번호에 관련된 신고 조회
    List<Report> findAllByReservationId(Long reservationId);

    List<Report> findByReservation_StudentId(Long studentId);

    List<Report> findByReservation_ReservationId(String reservationId);
}

