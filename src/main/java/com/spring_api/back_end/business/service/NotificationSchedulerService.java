package com.spring_api.back_end.business.service;

import com.spring_api.back_end.data.entity.CalendarAlert;
import com.spring_api.back_end.data.entity.Notification;
import com.spring_api.back_end.data.enums.AlertStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "notification.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class NotificationSchedulerService {

    private final CalendarAlertService calendarAlertService;
    private final NotificationService notificationService;

    @Value("${notification.scheduler.fixed-rate:300000}")
    private long fixedRate;

    @Scheduled(fixedRateString = "${notification.scheduler.fixed-rate:300000}",
               initialDelayString = "${notification.scheduler.initial-delay:60000}")
    public void processAlerts() {
        log.debug("Processing alerts at: {}", LocalDateTime.now());

        // First check if there are any alerts due to avoid unnecessary queries
        long alertCount = calendarAlertService.countActiveAlertsDueForNotification();
        if (alertCount == 0) {
            log.debug("No alerts due for notification at this time");
            return;
        }

        log.info("Found {} alerts due for notification at {}", alertCount, LocalDateTime.now());

        List<CalendarAlert> dueAlerts = calendarAlertService.findActiveAlertsDueForNotification();

        for (CalendarAlert alert : dueAlerts) {
            try {
                // Create notification
                String message = createNotificationMessage(alert);
                Notification notification = Notification.builder()
                        .email(alert.getUserEmail())
                        .message(message)
                        .calendarAlert(alert)
                        .build();

                notificationService.save(notification);

                // Update alert status to completed
                alert.setStatus(AlertStatus.COMPLETED);
                calendarAlertService.updateAlert(alert);

                log.info("Notification created for alert ID: {} for user: {}",
                        alert.getId(), alert.getUserEmail());

            } catch (Exception e) {
                log.error("Error processing alert ID: {} for user: {}",
                        alert.getId(), alert.getUserEmail(), e);
            }
        }
    }

    private String createNotificationMessage(CalendarAlert alert) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder message = new StringBuilder();

        message.append("🔔 Calendar Alert Notification\n\n");
        message.append("Alert Time: ").append(alert.getAlertTime().format(formatter)).append("\n");

        if (alert.getAlertAddress() != null && !alert.getAlertAddress().isEmpty()) {
            message.append("Location: ").append(alert.getAlertAddress()).append("\n");
        }

        message.append("Note: ").append(alert.getNote()).append("\n\n");
        message.append("This alert was scheduled for ").append(alert.getAlertTime().format(formatter));

        return message.toString();
    }

    @Scheduled(cron = "0 0 1 * * ?") // Run daily at 1 AM
    public void cleanupExpiredAlerts() {
        log.info("Cleaning up expired alerts at: {}", LocalDateTime.now());

        // Mark alerts that are 30 days past their alert time as expired
        LocalDateTime expiredThreshold = LocalDateTime.now().minusDays(30);
        List<CalendarAlert> expiredAlerts = calendarAlertService.findActiveAlertsDueForNotification();

        for (CalendarAlert alert : expiredAlerts) {
            if (alert.getAlertTime().isBefore(expiredThreshold) &&
                alert.getStatus() == AlertStatus.ACTIVE) {
                alert.setStatus(AlertStatus.EXPIRED);
                calendarAlertService.updateAlert(alert);
                log.info("Expired alert ID: {} for user: {}", alert.getId(), alert.getUserEmail());
            }
        }
    }
}
