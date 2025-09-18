package com.ridehailing.driverservice.controller;

import com.ridehailing.driverservice.dto.DriverLocationDto; // Import the new DTO
import com.ridehailing.driverservice.model.Driver;
import com.ridehailing.driverservice.repository.DriverRepository;
import com.ridehailing.driverservice.service.DriverLocationService; // Import the new Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ridehailing.driverservice.model.DriverStatus;
import java.util.Optional;
import java.util.Map;

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

    // --- ADD THE NEW STATUS UPDATE ENDPOINT ---
    @PostMapping("/{driverId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long driverId,
            @RequestBody Map<String, String> statusUpdate) {

        Optional<Driver> driverOptional = driverRepository.findById(driverId);
        if (driverOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Driver not found");
        }

        Driver driver = driverOptional.get();
        DriverStatus newStatus = DriverStatus.valueOf(statusUpdate.get("status").toUpperCase());
        driver.setStatus(newStatus);
        driverRepository.save(driver);

        // If driver goes offline, remove them from Redis
        if (newStatus == DriverStatus.OFFLINE) {
            driverLocationService.removeDriverLocation(driverId);
        }

        return ResponseEntity.ok("Status updated successfully for driver " + driverId);
    }

    // --- ADD THE NEW ENDPOINT BELOW ---
    @PostMapping("/{driverId}/location")
    public ResponseEntity<String> updateLocation(
            @PathVariable Long driverId,
            @RequestBody DriverLocationDto locationDto) {

        // --- ADD THIS CHECK ---
        Optional<Driver> driverOptional = driverRepository.findById(driverId);
        if (driverOptional.isEmpty() || driverOptional.get().getStatus() == DriverStatus.OFFLINE) {
            return ResponseEntity.status(400).body("Driver is not online or does not exist.");
        }

        driverLocationService.updateDriverLocation(
            driverId,
            locationDto.getLongitude(),
            locationDto.getLatitude()
        );
        return ResponseEntity.ok("Location updated successfully for driver " + driverId);
    }
}