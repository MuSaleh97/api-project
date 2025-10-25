package com.spring_api.back_end.facade;

import com.spring_api.back_end.client.AlAdhanClient;
import com.spring_api.back_end.data.dto.NextPrayerInfo;
import com.spring_api.back_end.data.dto.request.CountryRequest;
import com.spring_api.back_end.data.dto.response.AdhanResponse;
import com.spring_api.back_end.data.dto.response.adhan.PrayerTimesResponse;
import com.spring_api.back_end.data.dto.response.adhan.Timings;
import com.spring_api.back_end.data.enums.Dictionary;
import com.spring_api.back_end.data.enums.Zone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FeaturesFacade {

    private final AlAdhanClient alAdhanClient;

    public AdhanResponse prayerTimes(CountryRequest countryRequest) {
        // Validate input
        if (countryRequest == null) {
            throw new com.spring_api.back_end.business.exception.InvalidRequestDataException("Country request cannot be null");
        }
        if (countryRequest.getCountry() == null || countryRequest.getCountry().trim().isEmpty()) {
            throw new com.spring_api.back_end.business.exception.InvalidRequestDataException("Country name is required");
        }
        if (countryRequest.getCity() == null || countryRequest.getCity().trim().isEmpty()) {
            throw new com.spring_api.back_end.business.exception.InvalidRequestDataException("City name is required");
        }

        try {
            // Resolve location names to English
            String englishCountry = Dictionary.resolveToEnglish(countryRequest.getCountry());
            String englishCity = Dictionary.resolveToEnglish(countryRequest.getCity());

            CountryRequest request = new CountryRequest(englishCountry, englishCity);

            // Call external API
            PrayerTimesResponse prayerTimesResponse;
            try {
                prayerTimesResponse = alAdhanClient.getTimingsByCity(request.getCity(), request.getCountry());
            } catch (Exception e) {
                throw new com.spring_api.back_end.business.exception.ExternalApiException("Failed to retrieve prayer times from external service", e);
            }

            // Validate response
            if (prayerTimesResponse == null || prayerTimesResponse.getData() == null) {
                throw new com.spring_api.back_end.business.exception.ExternalApiException("Invalid response received from prayer times service");
            }

            return adhanResponseBuilder(prayerTimesResponse, request.getCountry());

        } catch (com.spring_api.back_end.business.exception.InvalidLocationException |
                 com.spring_api.back_end.business.exception.InvalidZoneRegionException |
                 com.spring_api.back_end.business.exception.ExternalApiException |
                 com.spring_api.back_end.business.exception.InvalidRequestDataException e) {
            // Re-throw our custom exceptions
            throw e;
        } catch (Exception e) {
            throw new com.spring_api.back_end.business.exception.PrayerTimeCalculationException("Unexpected error occurred while calculating prayer times", e);
        }
    }

    public AdhanResponse adhanResponseBuilder(PrayerTimesResponse prayerTimesResponse, String country) {
        try {
            // Validate input parameters
            if (prayerTimesResponse == null || prayerTimesResponse.getData() == null) {
                throw new com.spring_api.back_end.business.exception.ExternalApiException("Invalid prayer times response received");
            }

            if (prayerTimesResponse.getData().getTimings() == null) {
                throw new com.spring_api.back_end.business.exception.ExternalApiException("Prayer timings not available in response");
            }

            if (country == null || country.trim().isEmpty()) {
                throw new com.spring_api.back_end.business.exception.InvalidRequestDataException("Country parameter is required");
            }

            Timings timings = prayerTimesResponse.getData().getTimings();

            // Validate that all prayer times are present
            if (timings.getFajr() == null || timings.getSunrise() == null ||
                timings.getDhuhr() == null || timings.getAsr() == null ||
                timings.getMaghrib() == null || timings.getIsha() == null) {
                throw new com.spring_api.back_end.business.exception.InvalidPrayerTimeFormatException("One or more prayer times are missing from the response");
            }

            // Get zone ID for the country
            String zoneId;
            try {
                zoneId = Zone.getZoneIdByRegion(country);
            } catch (Exception e) {
                throw new com.spring_api.back_end.business.exception.InvalidZoneRegionException("Unable to determine time zone for country: " + country);
            }

            // Calculate next prayer
            NextPrayerInfo nextPrayer;
            try {
                nextPrayer = getNextPrayer(
                    timings.getFajr(), timings.getSunrise(),
                    timings.getDhuhr(), timings.getAsr(),
                    timings.getMaghrib(), timings.getIsha(), zoneId
                );
            } catch (Exception e) {
                throw new com.spring_api.back_end.business.exception.PrayerTimeCalculationException("Error calculating next prayer time", e);
            }

            long hours = nextPrayer.getTimeRemaining().toHours();
            long minutes = nextPrayer.getTimeRemaining().minusHours(hours).toMinutes();
            long seconds = nextPrayer.getTimeRemaining()
                    .minusHours(hours)
                    .minusMinutes(minutes)
                    .toSeconds();

            String timeRemainingForTheNextPrayer = String.format("%02d:%02d:%02d", hours, minutes, seconds);

            // Validate date information
            if (prayerTimesResponse.getData().getDate() == null) {
                throw new com.spring_api.back_end.business.exception.ExternalApiException("Date information not available in response");
            }

            return AdhanResponse.builder()
                    .fajr(timings.getFajr())
                    .sunrise(timings.getSunrise())
                    .dhuhr(timings.getDhuhr())
                    .asr(timings.getAsr())
                    .maghrib(timings.getMaghrib())
                    .isha(timings.getIsha())
                    .gregorianCalendar(prayerTimesResponse.getData().getDate().getReadable())
                    .hijriCalendar(prayerTimesResponse.getData().getDate().getHijri() != null ?
                        prayerTimesResponse.getData().getDate().getHijri().getDate() : "N/A")
                    .theNextPrayer(nextPrayer.getPrayerName())
                    .TimeRemainingForTheNextPrayer(timeRemainingForTheNextPrayer)
                    .build();

        } catch (com.spring_api.back_end.business.exception.ExternalApiException |
                 com.spring_api.back_end.business.exception.InvalidPrayerTimeFormatException |
                 com.spring_api.back_end.business.exception.InvalidZoneRegionException |
                 com.spring_api.back_end.business.exception.PrayerTimeCalculationException |
                 com.spring_api.back_end.business.exception.InvalidRequestDataException e) {
            // Re-throw our custom exceptions
            throw e;
        } catch (Exception e) {
            throw new com.spring_api.back_end.business.exception.PrayerTimeCalculationException("Unexpected error building prayer times response", e);
        }
    }

    public static NextPrayerInfo getNextPrayer(String fajr, String sunrise, String dhuhr, String asr,
                                               String maghrib, String isha, String region) {
        try {
            // Validate input parameters
            if (fajr == null || sunrise == null || dhuhr == null || asr == null || maghrib == null || isha == null) {
                throw new com.spring_api.back_end.business.exception.InvalidPrayerTimeFormatException("One or more prayer times are null");
            }

            if (region == null || region.trim().isEmpty()) {
                throw new com.spring_api.back_end.business.exception.InvalidZoneRegionException("Region cannot be null or empty");
            }

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            ZoneId zoneId;
            try {
                zoneId = ZoneId.of(region);
            } catch (DateTimeException e) {
                throw new com.spring_api.back_end.business.exception.InvalidZoneRegionException("Invalid time zone region: " + region);
            }

            LocalDate today = LocalDate.now(zoneId);
            LocalTime nowTime = LocalTime.now(zoneId);
            LocalDateTime now = LocalDateTime.of(today, nowTime);

            Map<String, LocalTime> prayerTimes = new LinkedHashMap<>();

            try {
                prayerTimes.put("Fajr", LocalTime.parse(fajr.trim(), timeFormatter));
                prayerTimes.put("Sunrise", LocalTime.parse(sunrise.trim(), timeFormatter));
                prayerTimes.put("Dhuhr", LocalTime.parse(dhuhr.trim(), timeFormatter));
                prayerTimes.put("Asr", LocalTime.parse(asr.trim(), timeFormatter));
                prayerTimes.put("Maghrib", LocalTime.parse(maghrib.trim(), timeFormatter));
                prayerTimes.put("Isha", LocalTime.parse(isha.trim(), timeFormatter));
            } catch (DateTimeException e) {
                throw new com.spring_api.back_end.business.exception.InvalidPrayerTimeFormatException("Invalid prayer time format. Expected format: HH:mm", e);
            }

            for (Map.Entry<String, LocalTime> entry : prayerTimes.entrySet()) {
                LocalDateTime prayerDateTime = LocalDateTime.of(today, entry.getValue());
                if (!now.isAfter(prayerDateTime)) {
                    Duration remaining = Duration.between(now, prayerDateTime);
                    return new NextPrayerInfo(entry.getKey(), remaining);
                }
            }

            // If all prayers for today have passed, next prayer is tomorrow's Fajr
            LocalDate tomorrow = today.plusDays(1);
            LocalDateTime tomorrowFajr = LocalDateTime.of(tomorrow, prayerTimes.get("Fajr"));
            Duration total = Duration.between(now, tomorrowFajr);

            return new NextPrayerInfo("Fajr", total);

        } catch (com.spring_api.back_end.business.exception.InvalidPrayerTimeFormatException |
                 com.spring_api.back_end.business.exception.InvalidZoneRegionException e) {
            // Re-throw our custom exceptions
            throw e;
        } catch (Exception e) {
            throw new com.spring_api.back_end.business.exception.PrayerTimeCalculationException("Unexpected error calculating next prayer time", e);
        }
    }

}
