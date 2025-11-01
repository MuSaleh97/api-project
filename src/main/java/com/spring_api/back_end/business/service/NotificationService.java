package com.spring_api.back_end.business.service;

import com.spring_api.back_end.data.entity.Notification;
import com.spring_api.back_end.data.enums.NotificationStatus;
import com.spring_api.back_end.data.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    public Notification save(Notification notification) {
        return repository.save(notification);
    }

    public List<Notification> findByEmail(String email) {
        return repository.findByEmailAndIsDeletedOrderByCreatedAtDesc(email, false);
    }

    public List<Notification> findByEmailAndStatus(String email, NotificationStatus status) {
        return repository.findByEmailAndStatusAndIsDeletedOrderByCreatedAtDesc(email, status, false);
    }

    public Optional<Notification> findByIdAndEmail(Long id, String email) {
        return repository.findByIdAndEmailAndIsDeleted(id, email, false);
    }

    public Optional<Notification> findById(Long id) {
        return repository.findById(id);
    }

    public long countUnreadNotifications(String email) {
        return repository.countUnreadNotifications(email, NotificationStatus.UNREAD);
    }

    public List<Notification> findByCalendarAlertId(Long alertId) {
        return repository.findByCalendarAlertId(alertId);
    }

    public Notification markAsRead(Long id, String email) {
        Optional<Notification> notificationOpt = findByIdAndEmail(id, email);
        if (notificationOpt.isPresent()) {
            Notification notification = notificationOpt.get();
            notification.markAsRead();
            return repository.save(notification);
        }
        return null;
    }

    public void deleteById(Long id) {
        repository.findById(id).ifPresent(notification -> {
            notification.setDeleted(true);
            repository.save(notification);
        });
    }

    public void softDeleteByIdAndEmail(Long id, String email) {
        repository.findByIdAndEmailAndIsDeleted(id, email, false)
                .ifPresent(notification -> {
                    notification.setDeleted(true);
                    repository.save(notification);
                });
    }
}
