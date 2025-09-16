package com.spring_api.back_end.controller;

import com.spring_api.back_end.data.dto.request.LoginRequest;
import com.spring_api.back_end.data.dto.request.RegisterRequest;
import com.spring_api.back_end.data.dto.response.AccountResponse;
import com.spring_api.back_end.facade.AccountFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountFacade accountFacade;

    @PostMapping("/register")
    public ResponseEntity<AccountResponse> register(@RequestBody RegisterRequest request) throws Exception {
        return new ResponseEntity<>(accountFacade.register(request), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/login")
    public ResponseEntity<AccountResponse> login(@RequestBody LoginRequest request) throws Exception {
        return new ResponseEntity<>(accountFacade.login(request), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<AccountResponse> delete(@RequestBody LoginRequest request) throws Exception {
        return new ResponseEntity<>(accountFacade.deleteAccount(request), HttpStatusCode.valueOf(200));
    }

}
