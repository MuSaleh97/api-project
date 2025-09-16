package com.spring_api.back_end.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Zone {
    JORDAN("Jordan", "Asia/Amman"),
    USA("USA", "America/New_York"),
    UK("United Kingdom", "Europe/London"),
    INDIA("India", "Asia/Kolkata");

    private final String region;
    private final String zoneId;

    public static String getZoneIdByRegion(String region) {
        for (Zone zone : Zone.values()) {
            if (zone.getRegion().equalsIgnoreCase(region)) {
                return zone.getZoneId();
            }
        }
        throw new IllegalArgumentException("No zone ID found for region: " + region);
    }
}
