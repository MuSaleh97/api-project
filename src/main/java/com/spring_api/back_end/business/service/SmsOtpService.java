package com.spring_api.back_end.business.service;

import com.spring_api.back_end.data.entity.OtpPhone;
import com.spring_api.back_end.data.repository.OtpPhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SmsOtpService {

    private final OtpPhoneRepository otpPhoneRepository;

    public void saveOtpPhone(OtpPhone otpPhone) {
        otpPhoneRepository.save(otpPhone);
    }

    public Optional<OtpPhone> getLatestOtpByPhone(String phone) {
        return otpPhoneRepository.findTopByPhoneOrderByIdDesc(phone);
    }

    public void deleteOtpPhone(OtpPhone otpPhone) {
        otpPhoneRepository.delete(otpPhone);
    }
}
