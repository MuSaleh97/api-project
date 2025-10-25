package com.spring_api.back_end.controller;

import com.spring_api.back_end.facade.OtpEmailFacade;
import com.spring_api.back_end.facade.SmsOtpFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/send-otp")
@RequiredArgsConstructor
public class SendOtpController {

    private final OtpEmailFacade emailFacade;
    private final SmsOtpFacade smsFacade;

    @PostMapping("/send-to-email")
    public ResponseEntity<String> sendOtpToEmail(@RequestParam String email) throws IOException {
        return new ResponseEntity<>(emailFacade.sendOtp(email), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/send-to-phone")
    public ResponseEntity<String> sendOtpToPhone(
            @RequestParam String phone,
            @RequestParam(required = false) String countryCode) {

        // Validate input
        if (phone == null || phone.trim().isEmpty()) {
            throw new com.spring_api.back_end.business.exception.InvalidRequestDataException("Phone number is required");
        }

        String result = smsFacade.sendOtpToPhone(phone, countryCode);
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }
}
