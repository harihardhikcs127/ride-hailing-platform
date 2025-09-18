package com.ridehailing.tripservice.controller;

import com.ridehailing.tripservice.dto.TripRequestDto;
import com.ridehailing.tripservice.model.Trip;
import com.ridehailing.tripservice.model.TripStatus;
import com.ridehailing.tripservice.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/trip")
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @PostMapping("/request")
    public ResponseEntity<Trip> requestTrip(@RequestBody TripRequestDto tripRequest) {
        Trip newTrip = new Trip();
        newTrip.setPassengerId(tripRequest.getPassengerId());
        newTrip.setPickupLocation(tripRequest.getPickupLocation());
        newTrip.setDropoffLocation(tripRequest.getDropoffLocation());
        newTrip.setRequestTime(LocalDateTime.now());
        newTrip.setStatus(TripStatus.REQUESTED);
        
        Trip savedTrip = tripRepository.save(newTrip);
        return ResponseEntity.ok(savedTrip);
    }
}