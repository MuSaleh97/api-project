package com.spring_api.back_end.data.entity;

import com.spring_api.back_end.data.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "NOTIFICATION")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "MESSAGE", columnDefinition = "TEXT", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private NotificationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALERT_ID")
    private CalendarAlert calendarAlert;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "READ_AT")
    private LocalDateTime readAt;

    @Column(name = "IS_DELETED")
    private boolean isDeleted;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = NotificationStatus.UNREAD;
        }
        if (isDeleted == false) {
            isDeleted = false;
        }
    }

    public void markAsRead() {
        this.status = NotificationStatus.READ;
        this.readAt = LocalDateTime.now();
    }
}
