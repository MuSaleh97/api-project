package com.spring_api.back_end.data.dto.request;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String emailOrPhone;
    private String newPassword;
    private String confirmNewPassword;
}
