package com.ridehailing.matchservice.dto;

public class FindDriverResponseDto {
    private Long driverId;

    public FindDriverResponseDto(Long driverId) {
        this.driverId = driverId;
    }

    // Getter and Setter
    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }
}