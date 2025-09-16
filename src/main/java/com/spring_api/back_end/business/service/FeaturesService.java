package com.spring_api.back_end.business.service;

import com.spring_api.back_end.data.entity.AlAdhanMethod;
import com.spring_api.back_end.data.repository.AlAdhanMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeaturesService {
    private final AlAdhanMethodRepository alAdhanMethodRepository;

    public AlAdhanMethod findAlAdhanMethodByCountry(String country){
        return alAdhanMethodRepository.findAlAdhanMethodByCountry(country);
    }
}
