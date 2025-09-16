package com.spring_api.back_end.data.repository;

import com.spring_api.back_end.data.entity.AlAdhanMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlAdhanMethodRepository extends JpaRepository<AlAdhanMethod, Long> {
    AlAdhanMethod findAlAdhanMethodByCountry(String country);
}
