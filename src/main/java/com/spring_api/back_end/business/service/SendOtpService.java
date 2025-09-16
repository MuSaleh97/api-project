package com.spring_api.back_end.business.service;

import com.spring_api.back_end.data.entity.OtpEmail;
import com.spring_api.back_end.data.repository.OtpEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendOtpService {
    private final OtpEmailRepository otpEmailRepository;

    public void saveOtpEmail(OtpEmail otpEmail) {
        otpEmailRepository.save(otpEmail);
    }

}
