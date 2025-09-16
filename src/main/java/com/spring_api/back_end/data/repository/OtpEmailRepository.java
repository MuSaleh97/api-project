package com.spring_api.back_end.data.repository;

import com.spring_api.back_end.data.entity.OtpEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpEmailRepository extends JpaRepository<OtpEmail, Long> {

    Optional<OtpEmail> findTopByEmailOrderByIdDesc(String email);

}
