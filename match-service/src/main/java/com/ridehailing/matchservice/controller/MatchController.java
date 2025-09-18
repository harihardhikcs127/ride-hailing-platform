package com.ridehailing.matchservice.controller;

import com.ridehailing.matchservice.dto.FindDriverRequestDto;
import com.ridehailing.matchservice.dto.FindDriverResponseDto;
import com.ridehailing.matchservice.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @PostMapping("/find-driver")
    public ResponseEntity<?> findDriver(@RequestBody FindDriverRequestDto requestDto) {
        Long driverId = matchService.findNearestDriver(requestDto.getLongitude(), requestDto.getLatitude());

        if (driverId == null) {
            return ResponseEntity.status(404).body("No drivers available in your area.");
        }

        return ResponseEntity.ok(new FindDriverResponseDto(driverId));
    }
}