package com.ridehailing.driverservice.controller;

import com.ridehailing.driverservice.model.Driver;
import com.ridehailing.driverservice.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;

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
}