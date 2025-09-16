package com.spring_api.back_end.business.service;

import com.spring_api.back_end.data.entity.OtpEmail;
import com.spring_api.back_end.data.repository.OtpEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerifyOtpService {
    private final OtpEmailRepository otpEmailRepository;

    public OtpEmail getLatestOtpToEmail(String email){
        return otpEmailRepository.findTopByEmailOrderByIdDesc(email).get();
    }
}
