package com.ridehailing.matchservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults; // Import GeoResults
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private static final String DRIVER_GEO_KEY = "driver_locations";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Long findNearestDriver(double longitude, double latitude) {
        Distance radius = new Distance(10, Metrics.KILOMETERS);
        Point passengerLocation = new Point(longitude, latitude);
        Circle searchArea = new Circle(passengerLocation, radius);

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance()
                .limit(1);

        // The result is now stored in a GeoResults object
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo()
                .radius(DRIVER_GEO_KEY, searchArea, args);

        // --- THIS IS THE FIX ---
        // First, check if the results object itself is null
        if (results == null) {
            System.out.println("Redis query returned null. No drivers found.");
            return null;
        }

        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> content = results.getContent();

        // Second, check if the content list is empty
        if (content.isEmpty()) {
            System.out.println("No drivers found nearby.");
            return null;
        }

        GeoResult<RedisGeoCommands.GeoLocation<String>> nearest = content.get(0);
        String driverId = nearest.getContent().getName();
        System.out.println("Found nearest driver: " + driverId + " at " + nearest.getDistance().getValue() + " km");

        return Long.parseLong(driverId);
    }
}