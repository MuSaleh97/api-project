package com.spring_api.back_end.data.entity;

import com.spring_api.back_end.data.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.crypto.SecretKey;

@Entity
@Data
@Table(name = "ACCOUNT")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @Column(name = "ID_MEMBER")
    private Long id;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE_NUMBER")
    private String phone;

    @Column(name = "DATE_OF_BIRTH")
    private String dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER")
    private Gender gender;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IS_DELETED")
    private boolean isDeleted;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @Column(name = "SECRET_KEY")
    private SecretKey secretKey;

    @Column(name = "VERIFY_EMAIL")
    private boolean verifyEmail;

    @Column(name = "VERIFY_MOBILE_NUMBER")
    private boolean verifyPhone;
}