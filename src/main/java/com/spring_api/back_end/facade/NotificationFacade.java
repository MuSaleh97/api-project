package com.spring_api.back_end.facade;

import com.spring_api.back_end.business.service.NotificationService;
import com.spring_api.back_end.data.dto.request.GetNotificationsRequest;
import com.spring_api.back_end.data.dto.response.NotificationResponse;
import com.spring_api.back_end.data.entity.Notification;
import com.spring_api.back_end.data.enums.NotificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationFacade {

    private final NotificationService notificationService;

    public List<NotificationResponse> getNotifications(GetNotificationsRequest request) {
        List<Notification> notifications;

        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            try {
                NotificationStatus status = NotificationStatus.valueOf(request.getStatus().toUpperCase());
                notifications = notificationService.findByEmailAndStatus(request.getEmail(), status);
            } catch (IllegalArgumentException e) {
                // Invalid status, return all notifications
                notifications = notificationService.findByEmail(request.getEmail());
            }
        } else {
            notifications = notificationService.findByEmail(request.getEmail());
        }

        return notifications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public NotificationResponse markNotificationAsRead(Long notificationId, String email) {
        Notification notification = notificationService.markAsRead(notificationId, email);

        if (notification != null) {
            return mapToResponse(notification);
        }

        throw new RuntimeException("Notification not found or access denied");
    }

    public void deleteNotification(Long notificationId, String email) {
        Optional<Notification> notificationOpt = notificationService.findByIdAndEmail(notificationId, email);

        if (notificationOpt.isPresent()) {
            notificationService.softDeleteByIdAndEmail(notificationId, email);
        } else {
            throw new RuntimeException("Notification not found or access denied");
        }
    }

    public long getUnreadNotificationCount(String email) {
        return notificationService.countUnreadNotifications(email);
    }

    public List<NotificationResponse> getUnreadNotifications(String email) {
        List<Notification> notifications = notificationService.findByEmailAndStatus(email, NotificationStatus.UNREAD);
        return notifications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public NotificationResponse getNotificationById(Long id, String email) {
        Optional<Notification> notificationOpt = notificationService.findByIdAndEmail(id, email);

        if (notificationOpt.isPresent()) {
            return mapToResponse(notificationOpt.get());
        }

        throw new RuntimeException("Notification not found or access denied");
    }

    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .email(notification.getEmail())
                .message(notification.getMessage())
                .status(notification.getStatus())
                .alertId(notification.getCalendarAlert() != null ? notification.getCalendarAlert().getId() : null)
                .createdAt(notification.getCreatedAt())
                .readAt(notification.getReadAt())
                .build();
    }
}
