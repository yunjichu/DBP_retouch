package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Reservation;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class JpaReservationRepository implements ReservationRepository{

    private final EntityManager em;

    public JpaReservationRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * 예약 데이터 저장
     * - JPA의 persist 메서드를 사용하여 데이터 저장.
     */
    @Override
    public Reservation save(Reservation reservation) {
        em.persist(reservation);
        return reservation;
    }

    /**
     * 예약 데이터 삭제
     * - JPA의 remove 메서드를 사용하여 데이터 삭제.
     */
    @Override
    public void deleteById(Long reservationId) {
        Reservation reservation = em.find(Reservation.class, reservationId);
        if (reservation != null) {
            em.remove(reservation);
        }
    }

    /**
     * 예약 데이터 수정
     * - JPA는 persist를 호출하지 않아도 관리되는 엔티티를 변경하면 자동으로 업데이트됩니다.
     */
    @Override
    public Reservation update(Long reservationId, Reservation reservation) {
        Reservation reservation1 = em.find(Reservation.class, reservationId);
        if (reservation1 != null) {
            reservation1.setStudentId(reservation.getStudentId());
            reservation1.setFacilityId(reservation.getFacilityId());
            reservation1.setReservationDate(reservation.getReservationDate());
            reservation1.setReservationTime(reservation.getReservationTime());
        }
        return reservation1;
    }

    /**
     * 모든 예약 데이터 조회
     * - JPQL을 사용하여 모든 데이터를 조회.
     */
    @Override
    public List<Reservation> findAll() {
        return em.createQuery("select r from Reservation r", Reservation.class)
                .getResultList();
    }

    /**
     * 특정 학생 ID로 예약 데이터 조회
     * - JPQL을 사용하여 STUDENT_ID로 필터링.
     */
    @Override
    public List<Reservation> findByStudentId(Long studentId) {
        return em.createQuery("select r from Reservation r where r.studentId = :studentId", Reservation.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }

    /**
     * 특정 시설, 날짜, 시간대 예약 상태 확인
     * - JPQL을 사용하여 COUNT로 존재 여부 확인.
     */
    @Override
    public boolean existsByFacilityIdAndReservationDateAndReservationTime(Long facilityId, LocalDate reservationDate, LocalTime reservationTime) {
        Long count = em.createQuery(
                        "select count(r) from Reservation r where r.facilityId = :facilityId and r.reservationDate = :reservationDate and r.reservationTime = :reservationTime",
                        Long.class)
                .setParameter("facilityId", facilityId)
                .setParameter("reservationDate", reservationDate)
                .setParameter("reservationTime", reservationTime)
                .getSingleResult();
        return count > 0;
    }

    /**
     * 예약 ID로 예약 상태 확인
     * - JPA의 find 메서드를 사용하여 ID 존재 여부 확인.
     */
    @Override
    public boolean existsById(Long reservationId) {
        Reservation reservation = em.find(Reservation.class, reservationId);
        return reservation != null;
    }
}
