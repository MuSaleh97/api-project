package com.spring_api.back_end.data.dto.response;

import com.spring_api.back_end.data.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private String email;
    private String message;
    private NotificationStatus status;
    private Long alertId;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
}
