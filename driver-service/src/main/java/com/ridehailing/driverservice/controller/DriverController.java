package com.ridehailing.driverservice.controller;

import com.ridehailing.driverservice.dto.DriverLocationDto; // Import the new DTO
import com.ridehailing.driverservice.model.Driver;
import com.ridehailing.driverservice.repository.DriverRepository;
import com.ridehailing.driverservice.service.DriverLocationService; // Import the new Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired // Inject the new service
    private DriverLocationService driverLocationService;

    @PostMapping("/register")
    public ResponseEntity<Driver> registerDriver(@RequestBody Driver driver) {
        Driver savedDriver = driverRepository.save(driver);
        return ResponseEntity.ok(savedDriver);
    }

    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverRepository.findAll();
        return ResponseEntity.ok(drivers);
    }

    // --- ADD THE NEW ENDPOINT BELOW ---
    @PostMapping("/{driverId}/location")
    public ResponseEntity<String> updateLocation(
            @PathVariable Long driverId,
            @RequestBody DriverLocationDto locationDto) {
        driverLocationService.updateDriverLocation(
            driverId,
            locationDto.getLongitude(),
            locationDto.getLatitude()
        );
        return ResponseEntity.ok("Location updated successfully for driver " + driverId);
    }
}