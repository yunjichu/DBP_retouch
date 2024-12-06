package Facility.reservation.DBP.service;

import Facility.reservation.DBP.entity.Facility;
import Facility.reservation.DBP.reporsitory.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private ReservationService reservationService;

    // 모든 시설 조회
    public List<Facility> getAllFacilities() {
        List<Facility> facilities = facilityRepository.findAll();
        enrichFacilityData(facilities, null, null);
        return facilities;
    }

    // Facility ID로 시설 조회
    public Facility getFacilityById(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Facility ID를 가진 시설이 존재하지 않습니다: " + facilityId));
        enrichFacilityData(List.of(facility), null, null);
        return facility;
    }

    // 위치로 시설 검색
    public List<Facility> getFacilitiesByLocation(String location) {
        List<Facility> facilities = facilityRepository.findAll().stream()
                .filter(facility -> facility.getLocation() != null && facility.getLocation().contains(location))
                .collect(Collectors.toList());
        enrichFacilityData(facilities, null, null);
        return facilities;
    }

    // 위치와 날짜로 시설 검색
    public List<Facility> getFacilitiesByLocationAndDate(String location, LocalDate date) {
        List<Facility> facilities = location == null || location.isEmpty()
                ? facilityRepository.findAll()
                : facilityRepository.findAll().stream()
                .filter(facility -> facility.getLocation() != null && facility.getLocation().contains(location))
                .collect(Collectors.toList());

        enrichFacilityData(facilities, date, null);
        return facilities;
    }

    // 데이터 동적 추가
    private void enrichFacilityData(List<Facility> facilities, LocalDate date, Integer timeSlot) {
        facilities.forEach(facility -> {
            // 예약 횟수 설정
            int reservationCount = reservationService.getCountByLocation(facility.getLocation());
            facility.setReservationCount(reservationCount);

            // 예약 가능 여부 설정
            boolean isAvailable = date == null || reservationService.isFacilityAvailable(facility.getFacilityId(), date, timeSlot);
            facility.setAvailable(isAvailable);
        });
    }
}
