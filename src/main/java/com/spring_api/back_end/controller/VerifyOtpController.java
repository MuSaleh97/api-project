package com.spring_api.back_end.controller;

import com.spring_api.back_end.facade.OtpEmailFacade;
import com.spring_api.back_end.facade.SmsOtpFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        originPatterns = "*",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
        allowCredentials = "true",
        maxAge = 3600
)
@RestController
@RequestMapping("/verify-otp")
@RequiredArgsConstructor
public class VerifyOtpController {

    private final OtpEmailFacade emailFacade;
    private final SmsOtpFacade smsFacade;

    @PostMapping("/verify-to-email")
    public ResponseEntity<Boolean> verifyEmailOtp(@RequestParam String email, @RequestParam String otp) {
        return new ResponseEntity<>(emailFacade.emailVerify(email, otp), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/verify-to-phone")
    public ResponseEntity<Boolean> verifyPhoneOtp(
            @RequestParam String phone,
            @RequestParam String otp,
            @RequestParam(required = false) String countryCode) {

        // Validate input
        if (phone == null || phone.trim().isEmpty()) {
            throw new com.spring_api.back_end.business.exception.InvalidRequestDataException("Phone number is required");
        }
        if (otp == null || otp.trim().isEmpty()) {
            throw new com.spring_api.back_end.business.exception.InvalidRequestDataException("OTP is required");
        }

        boolean result = smsFacade.verifyPhoneOtp(phone, otp, countryCode);
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }
}
