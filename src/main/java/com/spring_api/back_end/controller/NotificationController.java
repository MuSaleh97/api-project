package com.spring_api.back_end.controller;

import com.spring_api.back_end.data.dto.request.GetNotificationsRequest;
import com.spring_api.back_end.data.dto.response.NotificationResponse;
import com.spring_api.back_end.facade.NotificationFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Management", description = "APIs for managing calendar notifications")
public class NotificationController {

    private final NotificationFacade notificationFacade;

    @PostMapping("/get")
    @Operation(summary = "Get notifications", description = "Retrieves notifications for a user based on email and optional status filter")
    public ResponseEntity<List<NotificationResponse>> getNotifications(@Valid @RequestBody GetNotificationsRequest request) {
        try {
            List<NotificationResponse> notifications = notificationFacade.getNotifications(request);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/{email}")
    @Operation(summary = "Get all notifications for user", description = "Retrieves all notifications for a specific user")
    public ResponseEntity<List<NotificationResponse>> getAllNotifications(@PathVariable String email) {
        try {
            GetNotificationsRequest request = GetNotificationsRequest.builder()
                    .email(email)
                    .build();
            List<NotificationResponse> notifications = notificationFacade.getNotifications(request);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/{email}/unread")
    @Operation(summary = "Get unread notifications", description = "Retrieves all unread notifications for a user")
    public ResponseEntity<List<NotificationResponse>> getUnreadNotifications(@PathVariable String email) {
        try {
            List<NotificationResponse> notifications = notificationFacade.getUnreadNotifications(email);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/{email}/count")
    @Operation(summary = "Get unread notification count", description = "Gets the count of unread notifications for a user")
    public ResponseEntity<Map<String, Long>> getUnreadNotificationCount(@PathVariable String email) {
        try {
            long count = notificationFacade.getUnreadNotificationCount(email);
            return ResponseEntity.ok(Map.of("unreadCount", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "Mark notification as read", description = "Marks a specific notification as read")
    public ResponseEntity<NotificationResponse> markNotificationAsRead(
            @PathVariable Long id,
            @RequestParam String email) {
        try {
            NotificationResponse notification = notificationFacade.markNotificationAsRead(id, email);
            return ResponseEntity.ok(notification);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notification by ID", description = "Retrieves a specific notification by ID")
    public ResponseEntity<NotificationResponse> getNotificationById(
            @PathVariable Long id,
            @RequestParam String email) {
        try {
            NotificationResponse notification = notificationFacade.getNotificationById(id, email);
            return ResponseEntity.ok(notification);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete notification", description = "Deletes a specific notification")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Long id,
            @RequestParam String email) {
        try {
            notificationFacade.deleteNotification(id, email);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
