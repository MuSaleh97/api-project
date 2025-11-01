package com.spring_api.back_end.data.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddAlertRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "User email is required")
    private String userEmail;

    private String alertAddress;

    @NotBlank(message = "Note is required")
    private String note;

    @NotNull(message = "Alert time is required")
    private LocalDateTime alertTime;
}
