package com.spring_api.back_end.controller;

import com.spring_api.back_end.data.dto.request.ChangePasswordRequest;
import com.spring_api.back_end.data.dto.request.ForgotPasswordRequest;
import com.spring_api.back_end.data.dto.request.ProfileDataRequest;
import com.spring_api.back_end.data.dto.response.AccountResponse;
import com.spring_api.back_end.facade.SettingFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/setting")
@RequiredArgsConstructor
public class SettingController {
    private final SettingFacade facade;

    @PostMapping("/change-password")
    public ResponseEntity<AccountResponse> changePassword(@RequestBody ChangePasswordRequest request) throws Exception {
        return new ResponseEntity<>(facade.changePassword(request), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<AccountResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) throws Exception {
        return new ResponseEntity<>(facade.forgotPassword(request), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/update-profile")
    public ResponseEntity<AccountResponse> updateProfile(@RequestBody ProfileDataRequest request) throws Exception {
        return new ResponseEntity<>(facade.updateProfile(request), HttpStatusCode.valueOf(200));
    }
}
