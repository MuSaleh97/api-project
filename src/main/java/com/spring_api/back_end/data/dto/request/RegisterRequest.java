package com.spring_api.back_end.data.dto.request;

import com.spring_api.back_end.data.enums.Gender;
import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String phone;
    private String dob;
    private Gender gender;
    private String password;
    private String confirmPassword;
}
