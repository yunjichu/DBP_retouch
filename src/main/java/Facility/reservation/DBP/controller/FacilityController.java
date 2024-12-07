package Facility.reservation.DBP.controller;

import Facility.reservation.DBP.domain.Facility;
import Facility.reservation.DBP.service.FacilityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping("/{facilityName}")
    public Facility getFacilityByName(@PathVariable String facilityName) {
        return facilityService.getFacilityByName(facilityName);
    }

    @GetMapping
    public List<Facility> getAllFacilities() {
        return facilityService.getAllFacilities();
    }

    @GetMapping("/availability")
    public boolean checkAvailability(@RequestParam String facilityName, @RequestParam Integer timeSlot) {
        return facilityService.isFacilityAvailable(facilityName, timeSlot);
    }
}


