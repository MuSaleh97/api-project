package com.spring_api.back_end.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Zone {
    // Arab Countries - Middle East
    JORDAN("Jordan", "Asia/Amman"),
    SAUDI_ARABIA("Saudi Arabia", "Asia/Riyadh"),
    UAE("United Arab Emirates", "Asia/Dubai"),
    EGYPT("Egypt", "Africa/Cairo"),
    IRAQ("Iraq", "Asia/Baghdad"),
    SYRIA("Syria", "Asia/Damascus"),
    LEBANON("Lebanon", "Asia/Beirut"),
    PALESTINE("Palestine", "Asia/Jerusalem"),
    KUWAIT("Kuwait", "Asia/Kuwait"),
    QATAR("Qatar", "Asia/Qatar"),
    BAHRAIN("Bahrain", "Asia/Bahrain"),
    OMAN("Oman", "Asia/Muscat"),
    YEMEN("Yemen", "Asia/Aden"),

    // Arab Countries - North Africa
    ALGERIA("Algeria", "Africa/Algiers"),
    MOROCCO("Morocco", "Africa/Casablanca"),
    TUNISIA("Tunisia", "Africa/Tunis"),
    LIBYA("Libya", "Africa/Tripoli"),
    SUDAN("Sudan", "Africa/Khartoum"),

    // Arab Countries - Horn of Africa & Other
    SOMALIA("Somalia", "Africa/Mogadishu"),
    MAURITANIA("Mauritania", "Africa/Nouakchott"),
    DJIBOUTI("Djibouti", "Africa/Djibouti"),
    COMOROS("Comoros", "Indian/Comoro"),

    // Non-Arab Countries (existing)
    USA("USA", "America/New_York"),
    UK("United Kingdom", "Europe/London"),
    INDIA("India", "Asia/Kolkata");

    private final String region;
    private final String zoneId;

    public static String getZoneIdByRegion(String region) {
        if (region == null || region.trim().isEmpty()) {
            throw new com.spring_api.back_end.business.exception.InvalidRequestDataException("Region cannot be null or empty");
        }

        for (Zone zone : Zone.values()) {
            if (zone.getRegion().equalsIgnoreCase(region.trim())) {
                return zone.getZoneId();
            }
        }
        throw new com.spring_api.back_end.business.exception.InvalidZoneRegionException("No zone ID found for region: " + region + ". Please check the region name and ensure it matches one of the supported Arab countries or regions.");
    }
}
