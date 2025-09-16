package com.spring_api.back_end.data.dto.request;

import com.spring_api.back_end.data.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDataRequest {
    private String oldEmail;
    private String newFullName;
    private String newEmail;
    private String newPhone;
    private String newDob;
    private Gender newGender;
}
