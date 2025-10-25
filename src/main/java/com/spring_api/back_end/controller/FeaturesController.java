package com.spring_api.back_end.controller;

import com.spring_api.back_end.data.dto.request.CountryRequest;
import com.spring_api.back_end.data.dto.response.AdhanResponse;
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
    public ResponseEntity<AdhanResponse> prayerTimes(@RequestBody CountryRequest request) {
        // Validate request body
        if (request == null) {
            throw new com.spring_api.back_end.business.exception.InvalidRequestDataException("Request body is required");
        }

        AdhanResponse response = facade.prayerTimes(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }
}
