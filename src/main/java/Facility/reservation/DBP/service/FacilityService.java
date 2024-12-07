package Facility.reservation.DBP.service;

import Facility.reservation.DBP.domain.Facility;
import Facility.reservation.DBP.reporsitory.FacilityRepository;

import java.util.List;

public class FacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    public Facility getFacilityByName(String facilityName) {
        return facilityRepository.findByName(facilityName);
    }

    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    public boolean isFacilityAvailable(String facilityName, Integer timeSlot) {
        return facilityRepository.isAvailable(facilityName, timeSlot);
    }
}



