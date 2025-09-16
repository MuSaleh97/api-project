package com.spring_api.back_end.data.dto.response;

import com.spring_api.back_end.data.enums.Gender;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AccountResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String dob;
    private Gender gender;
    private boolean isDeleted;
    private boolean isActive;
}