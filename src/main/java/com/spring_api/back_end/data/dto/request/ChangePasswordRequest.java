package com.spring_api.back_end.data.dto.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String emailOrPhone;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
