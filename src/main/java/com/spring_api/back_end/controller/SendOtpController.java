package com.spring_api.back_end.controller;

import com.spring_api.back_end.facade.OtpEmailFacade;
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

    private final OtpEmailFacade facade;

    @PostMapping("/send-to-email")
    public ResponseEntity<String> sendOtp(@RequestParam String email) throws IOException {
        return new ResponseEntity<>(facade.sendOtp(email), HttpStatusCode.valueOf(200));
    }
}
