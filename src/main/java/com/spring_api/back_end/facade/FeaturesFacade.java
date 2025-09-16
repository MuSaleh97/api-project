package com.spring_api.back_end.facade;

import com.spring_api.back_end.business.service.FeaturesService;
import com.spring_api.back_end.client.AlAdhanClient;
import com.spring_api.back_end.data.dto.NextPrayerInfo;
import com.spring_api.back_end.data.dto.request.CountryRequest;
import com.spring_api.back_end.data.dto.response.AdhanResponse;
import com.spring_api.back_end.data.dto.response.adhan.Datas;
import com.spring_api.back_end.data.dto.response.adhan.PrayerTimesResponse;
import com.spring_api.back_end.data.dto.response.adhan.Timings;
import com.spring_api.back_end.data.entity.AlAdhanMethod;
import com.spring_api.back_end.data.enums.Dictionary;
import com.spring_api.back_end.data.enums.Zone;
import lombok.Builder;
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

    public AdhanResponse prayerTimes(CountryRequest countryRequest){
        CountryRequest request = new CountryRequest(Dictionary.resolveToEnglish(countryRequest.getCountry()), Dictionary.resolveToEnglish(countryRequest.getCity()));
        return adhanResponseBuilder(alAdhanClient.getTimingsByCity(request.getCity(), request.getCountry()), request.getCountry());
    }

    public AdhanResponse adhanResponseBuilder(PrayerTimesResponse prayerTimesResponse, String country){
        NextPrayerInfo nextPrayer = getNextPrayer(
                prayerTimesResponse.getData().getTimings().getFajr(), prayerTimesResponse.getData().getTimings().getSunrise(),
                prayerTimesResponse.getData().getTimings().getDhuhr(), prayerTimesResponse.getData().getTimings().getAsr(),
                prayerTimesResponse.getData().getTimings().getMaghrib(), prayerTimesResponse.getData().getTimings().getIsha(), Zone.getZoneIdByRegion(country)
        );

        long hours = nextPrayer.getTimeRemaining().toHours();
        long minutes = nextPrayer.getTimeRemaining().minusHours(hours).toMinutes();
        String timeRemainingForTheNextPrayer = String.format("%02d:%02d", hours, minutes);

        return AdhanResponse.builder()
                .fajr(prayerTimesResponse.getData().getTimings().getFajr())
                .sunrise(prayerTimesResponse.getData().getTimings().getSunrise())
                .dhuhr(prayerTimesResponse.getData().getTimings().getDhuhr())
                .asr(prayerTimesResponse.getData().getTimings().getAsr())
                .maghrib(prayerTimesResponse.getData().getTimings().getMaghrib())
                .isha(prayerTimesResponse.getData().getTimings().getIsha())
                .gregorianCalendar(prayerTimesResponse.getData().getDate().getReadable())
                .hijriCalendar(prayerTimesResponse.getData().getDate().getHijri().getDate())
                .theNextPrayer(nextPrayer.getPrayerName())
                .TimeRemainingForTheNextPrayer(timeRemainingForTheNextPrayer)
                .build();
    }

    public static NextPrayerInfo getNextPrayer(String fajr, String sunrise, String dhuhr, String asr,
                                               String maghrib, String isha, String region) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        ZoneId zoneId;
        try {
            zoneId = ZoneId.of(region);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid region: " + region);
        }

        LocalTime now = LocalTime.now(zoneId);

        Map<String, LocalTime> prayerTimes = new LinkedHashMap<>();
        prayerTimes.put("Fajr", LocalTime.parse(fajr, timeFormatter));
        prayerTimes.put("Sunrise", LocalTime.parse(sunrise, timeFormatter));
        prayerTimes.put("Dhuhr", LocalTime.parse(dhuhr, timeFormatter));
        prayerTimes.put("Asr", LocalTime.parse(asr, timeFormatter));
        prayerTimes.put("Maghrib", LocalTime.parse(maghrib, timeFormatter));
        prayerTimes.put("Isha", LocalTime.parse(isha, timeFormatter));

        for (Map.Entry<String, LocalTime> entry : prayerTimes.entrySet()) {
            LocalTime prayerTime = entry.getValue();
            if (!now.isAfter(prayerTime)) {
                Duration remaining = Duration.between(now, prayerTime);
                return new NextPrayerInfo(entry.getKey(), remaining);
            }
        }

        LocalTime tomorrowFajr = LocalTime.parse(fajr, timeFormatter);
        Duration untilMidnight = Duration.between(now, LocalTime.MIDNIGHT);
        Duration afterMidnightToFajr = Duration.between(LocalTime.MIN, tomorrowFajr);
        Duration total = untilMidnight.plus(afterMidnightToFajr);

        return new NextPrayerInfo("Fajr", total);
    }

}
