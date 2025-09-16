package com.spring_api.back_end.controller;

import com.spring_api.back_end.data.dto.request.CountryRequest;
import com.spring_api.back_end.data.dto.response.AdhanResponse;
import com.spring_api.back_end.data.dto.response.adhan.PrayerTimesResponse;
import com.spring_api.back_end.data.dto.response.adhan.Timings;
import com.spring_api.back_end.facade.FeaturesFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/feature")
@RequiredArgsConstructor
public class FeaturesController {

    private final FeaturesFacade facade;

    @PostMapping("/prayer-times")
    public ResponseEntity<AdhanResponse> prayerTimes(@RequestBody CountryRequest request) throws Exception {
        return new ResponseEntity<>(facade.prayerTimes(request), HttpStatusCode.valueOf(200));
    }
}
