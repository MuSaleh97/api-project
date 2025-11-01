package com.spring_api.back_end.data.dto.response;

import com.spring_api.back_end.data.enums.AlertStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarAlertResponse {

    private Long id;
    private String userEmail;
    private String alertAddress;
    private String note;
    private LocalDateTime alertTime;
    private AlertStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
