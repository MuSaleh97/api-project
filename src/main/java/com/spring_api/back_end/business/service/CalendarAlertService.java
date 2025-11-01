package com.spring_api.back_end.business.service;

import com.spring_api.back_end.data.entity.CalendarAlert;
import com.spring_api.back_end.data.enums.AlertStatus;
import com.spring_api.back_end.data.repository.CalendarAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalendarAlertService {

    private final CalendarAlertRepository repository;

    public CalendarAlert save(CalendarAlert alert) {
        return repository.save(alert);
    }

    public List<CalendarAlert> findByUserEmail(String userEmail) {
        return repository.findByUserEmailAndIsDeletedOrderByAlertTimeDesc(userEmail, false);
    }

    public List<CalendarAlert> findByUserEmailAndStatus(String userEmail, AlertStatus status) {
        return repository.findByUserEmailAndStatusAndIsDeletedOrderByAlertTimeDesc(userEmail, status, false);
    }

    public Optional<CalendarAlert> findByIdAndUserEmail(Long id, String userEmail) {
        return repository.findByIdAndUserEmailAndIsDeleted(id, userEmail, false);
    }

    public Optional<CalendarAlert> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.findById(id).ifPresent(alert -> {
            alert.setDeleted(true);
            repository.save(alert);
        });
    }

    public void softDeleteByIdAndUserEmail(Long id, String userEmail) {
        repository.findByIdAndUserEmailAndIsDeleted(id, userEmail, false)
                .ifPresent(alert -> {
                    alert.setDeleted(true);
                    repository.save(alert);
                });
    }

    public List<CalendarAlert> findActiveAlertsDueForNotification() {
        return repository.findActiveAlertsDueForNotification(LocalDateTime.now(), AlertStatus.ACTIVE);
    }

    public List<CalendarAlert> findAlertsByEmailAndTimeRange(String email, LocalDateTime startTime, LocalDateTime endTime) {
        return repository.findAlertsByEmailAndTimeRange(email, startTime, endTime);
    }

    public CalendarAlert updateAlert(CalendarAlert alert) {
        return repository.save(alert);
    }
}
