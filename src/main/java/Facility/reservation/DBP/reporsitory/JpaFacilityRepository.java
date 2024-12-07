package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Facility;
import jakarta.persistence.EntityManager;

import java.util.List;

public class JpaFacilityRepository implements FacilityRepository{

    private final EntityManager em;

    public JpaFacilityRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * 시설 이름으로 시설 정보를 조회합니다.
     *
     * @param facilityName 조회할 시설 이름
     * @return 조회된 Facility 객체
     */
    @Override
    public Facility findByName(String facilityName) {
        return em.createQuery("select f from Facility f where f.facilityName = :facilityName", Facility.class)
                .setParameter("facilityName", facilityName)
                .getSingleResult();
    }

    /**
     * 모든 시설 정보를 조회합니다.
     *
     * @return Facility 객체 리스트
     */
    @Override
    public List<Facility> findAll() {
        return em.createQuery("select f from Facility f", Facility.class)
                .getResultList();
    }

    /**
     * 특정 시간대에 시설이 예약 가능한지 확인합니다.
     *
     * @param facilityName 확인할 시설 이름
     * @param timeSlot 확인할 시간대
     * @return 예약 가능 여부 (true/false)
     */
    @Override
    public boolean isAvailable(String facilityName, Integer timeSlot) {
        Long count = em.createQuery(
                        "select count(f) from Facility f where f.facilityName = :facilityName and f.availableStartTime <= :timeSlot and f.availableEndTime >= :timeSlot",
                        Long.class)
                .setParameter("facilityName", facilityName)
                .setParameter("timeSlot", timeSlot)
                .getSingleResult();
        return count > 0;
    }
}
