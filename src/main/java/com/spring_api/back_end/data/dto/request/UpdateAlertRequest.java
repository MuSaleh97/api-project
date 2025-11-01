package com.spring_api.back_end.data.dto.request;

import com.spring_api.back_end.data.enums.AlertStatus;
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
public class UpdateAlertRequest {

    @NotNull(message = "Alert ID is required")
    private Long id;

    private String alertAddress;

    private String note;

    private LocalDateTime alertTime;

    private AlertStatus status;
}
