package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface FacilityRepository  {
    // 특정 시설 조회
    Optional<Facility> findByName(String facilityName);

    // 전체 시설 목록 조회
    List<Facility> findAll();

}
