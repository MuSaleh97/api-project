package com.spring_api.back_end.data.entity;

import com.spring_api.back_end.data.enums.AlertStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "CALENDAR_ALERT")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_EMAIL", nullable = false)
    private String userEmail;

    @Column(name = "ALERT_ADDRESS")
    private String alertAddress;

    @Column(name = "NOTE", columnDefinition = "TEXT")
    private String note;

    @Column(name = "ALERT_TIME", nullable = false)
    private LocalDateTime alertTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AlertStatus status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "IS_DELETED")
    private boolean isDeleted;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = AlertStatus.ACTIVE;
        }
        if (isDeleted == false) {
            isDeleted = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
