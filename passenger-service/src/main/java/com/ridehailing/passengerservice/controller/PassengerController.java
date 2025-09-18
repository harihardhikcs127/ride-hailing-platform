package com.ridehailing.passengerservice.controller;

import com.ridehailing.passengerservice.dto.RideRequestDto;
import com.ridehailing.passengerservice.model.Passenger;
import com.ridehailing.passengerservice.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passenger")
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepository;

    // Endpoint to register a new passenger
    @PostMapping("/register")
    public ResponseEntity<Passenger> registerPassenger(@RequestBody Passenger passenger) {
        Passenger savedPassenger = passengerRepository.save(passenger);
        return ResponseEntity.ok(savedPassenger);
    }

    // Endpoint to get all passengers
    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return ResponseEntity.ok(passengerRepository.findAll());
    }


    // Our previous endpoint for requesting a ride (no changes)
    @PostMapping("/request-ride")
    public ResponseEntity<String> requestRide(@RequestBody RideRequestDto rideRequestDto) {
        System.out.println("Received ride request for passenger: " + rideRequestDto.getPassengerId());
        System.out.println("From: " + rideRequestDto.getPickupLocation() + " To: " + rideRequestDto.getDropoffLocation());

        String responseMessage = "Ride request received for passenger " + rideRequestDto.getPassengerId() + ". Finding a driver...";
        return ResponseEntity.ok(responseMessage);
    }
}