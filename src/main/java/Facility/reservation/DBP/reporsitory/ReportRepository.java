package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Report;

import java.util.List;


public interface ReportRepository {

    // 신고 저장
    void save(Report report);

    // 학번으로 신고 내역 조회
    List<Report> findByReservationId(Long reservationId);
}

