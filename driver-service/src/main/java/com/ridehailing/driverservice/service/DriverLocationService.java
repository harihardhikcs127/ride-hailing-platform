package com.ridehailing.driverservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class DriverLocationService {

    // The key for our geospatial index in Redis
    private static final String DRIVER_GEO_KEY = "driver_locations";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void updateDriverLocation(Long driverId, double longitude, double latitude) {
        redisTemplate.opsForGeo().add(
            DRIVER_GEO_KEY,
            new Point(longitude, latitude),
            driverId.toString()
        );
        System.out.println("Updated location for driver " + driverId + " to [" + longitude + ", " + latitude + "]");
    }

    // --- ADD THIS NEW METHOD ---
    public void removeDriverLocation(Long driverId) {
        redisTemplate.opsForGeo().remove(DRIVER_GEO_KEY, driverId.toString());
        System.out.println("Removed location for driver " + driverId);
    }
}