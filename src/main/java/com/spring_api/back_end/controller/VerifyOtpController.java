package com.spring_api.back_end.controller;

import com.spring_api.back_end.facade.OtpEmailFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/verify-otp")
@RequiredArgsConstructor
public class VerifyOtpController {

    private final OtpEmailFacade facade;

    @PostMapping("/verify-to-email")
    public ResponseEntity<Boolean> sendOtp(@RequestParam String email, @RequestParam String otp) {
        return new ResponseEntity<>(facade.emailVerify(email, otp), HttpStatusCode.valueOf(200));
    }
}
