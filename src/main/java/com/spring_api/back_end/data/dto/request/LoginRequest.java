package com.spring_api.back_end.data.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailOrPhone;
    private String password;
}