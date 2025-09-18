package com.ridehailing.passengerservice.controller;

import com.ridehailing.passengerservice.dto.RideRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/passenger")
public class PassengerController {

    @PostMapping("/request-ride")
    public ResponseEntity<String> requestRide(@RequestBody RideRequestDto rideRequestDto) {
        System.out.println("Received ride request for passenger: " + rideRequestDto.getPassengerId());
        System.out.println("From: " + rideRequestDto.getPickupLocation() + " To: " + rideRequestDto.getDropoffLocation());

        // In the future, this will call the Match service.
        // For now, we just simulate success.
        String responseMessage = "Ride request received for passenger " + rideRequestDto.getPassengerId() + ". Finding a driver...";

        return ResponseEntity.ok(responseMessage);
    }
}