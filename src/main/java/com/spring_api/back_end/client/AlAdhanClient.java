package com.spring_api.back_end.client;

import com.spring_api.back_end.data.dto.response.adhan.PrayerTimesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "alAdhanClient", url = "${al.adhan.url}")
public interface AlAdhanClient {

    @GetMapping("timingsByCity")
    PrayerTimesResponse getTimingsByCity(
            @RequestParam("city") String city,
            @RequestParam("country") String country
    );
}
