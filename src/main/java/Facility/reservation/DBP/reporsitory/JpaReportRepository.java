package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Report;
import jakarta.persistence.EntityManager;

import java.util.List;

public class JpaReportRepository implements ReportRepository{

    private final EntityManager em;

    public JpaReportRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * 신고 데이터를 저장합니다.
     * @param report 저장할 신고 데이터
     */
    @Override
    public void save(Report report) {
        em.persist(report);
    }

    /**
     * 예약 ID로 신고 데이터를 조회합니다.
     * @param reservationId 조회할 예약 ID
     * @return 신고 데이터 리스트
     */
    @Override
    public List<Report> findByReservationId(Long reservationId) {
        return em.createQuery("select r from Report r where r.reservationId = :reservationId", Report.class)
                .setParameter("reservationId", reservationId)
                .getResultList();
    }
}
