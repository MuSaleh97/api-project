package com.spring_api.back_end.data.repository;

import com.spring_api.back_end.data.entity.OtpPhone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpPhoneRepository extends JpaRepository<OtpPhone, Long> {
    Optional<OtpPhone> findTopByPhoneOrderByIdDesc(String phone);
}
