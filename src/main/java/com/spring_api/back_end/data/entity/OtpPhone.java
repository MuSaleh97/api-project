package com.spring_api.back_end.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "OTP_PHONE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpPhone {

    @Id
    @Column(name = "OTP_ID")
    private Long id;

    @Column(name = "ID_MEMBER")
    private Long idMember;

    @Column(name = "MOBILE_NUMBER")
    private String phone;

    @Column(name = "OTP_TIMESTAMP")
    private Timestamp otpTimestamp;

    @Column(name = "OTP")
    private String otp;
}
