package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Facility;

import java.util.List;


public interface FacilityRepository {

    // 시설 이름으로 조회
    Facility findByName(String facilityName);

    // 모든 시설 조회
    List<Facility> findAll();

    // 예약 가능 여부 확인
    boolean isAvailable(String facilityName, Integer timeSlot);
}
