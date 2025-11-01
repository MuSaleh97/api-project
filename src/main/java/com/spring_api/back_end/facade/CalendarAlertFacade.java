package com.spring_api.back_end.facade;

import com.spring_api.back_end.business.service.CalendarAlertService;
import com.spring_api.back_end.data.dto.request.AddAlertRequest;
import com.spring_api.back_end.data.dto.request.DeleteAlertRequest;
import com.spring_api.back_end.data.dto.request.UpdateAlertRequest;
import com.spring_api.back_end.data.dto.response.CalendarAlertResponse;
import com.spring_api.back_end.data.entity.CalendarAlert;
import com.spring_api.back_end.data.enums.AlertStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CalendarAlertFacade {

    private final CalendarAlertService calendarAlertService;

    public CalendarAlertResponse addAlert(AddAlertRequest request) {
        CalendarAlert alert = CalendarAlert.builder()
                .userEmail(request.getUserEmail())
                .alertAddress(request.getAlertAddress())
                .note(request.getNote())
                .alertTime(request.getAlertTime())
                .status(AlertStatus.ACTIVE)
                .build();

        CalendarAlert savedAlert = calendarAlertService.save(alert);
        return mapToResponse(savedAlert);
    }

    public CalendarAlertResponse updateAlert(UpdateAlertRequest request, String userEmail) {
        Optional<CalendarAlert> alertOpt = calendarAlertService.findByIdAndUserEmail(request.getId(), userEmail);

        if (alertOpt.isPresent()) {
            CalendarAlert alert = alertOpt.get();

            if (request.getAlertAddress() != null) {
                alert.setAlertAddress(request.getAlertAddress());
            }
            if (request.getNote() != null) {
                alert.setNote(request.getNote());
            }
            if (request.getAlertTime() != null) {
                alert.setAlertTime(request.getAlertTime());
            }
            if (request.getStatus() != null) {
                alert.setStatus(request.getStatus());
            }

            CalendarAlert updatedAlert = calendarAlertService.updateAlert(alert);
            return mapToResponse(updatedAlert);
        }

        throw new RuntimeException("Alert not found or access denied");
    }

    public void deleteAlert(DeleteAlertRequest request, String userEmail) {
        Optional<CalendarAlert> alertOpt = calendarAlertService.findByIdAndUserEmail(request.getId(), userEmail);

        if (alertOpt.isPresent()) {
            calendarAlertService.softDeleteByIdAndUserEmail(request.getId(), userEmail);
        } else {
            throw new RuntimeException("Alert not found or access denied");
        }
    }

    public List<CalendarAlertResponse> getUserAlerts(String userEmail) {
        List<CalendarAlert> alerts = calendarAlertService.findByUserEmail(userEmail);
        return alerts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CalendarAlertResponse> getUserAlertsByStatus(String userEmail, AlertStatus status) {
        List<CalendarAlert> alerts = calendarAlertService.findByUserEmailAndStatus(userEmail, status);
        return alerts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CalendarAlertResponse getAlertById(Long id, String userEmail) {
        Optional<CalendarAlert> alertOpt = calendarAlertService.findByIdAndUserEmail(id, userEmail);

        if (alertOpt.isPresent()) {
            return mapToResponse(alertOpt.get());
        }

        throw new RuntimeException("Alert not found or access denied");
    }

    public List<CalendarAlertResponse> getAlertsInTimeRange(String userEmail, LocalDateTime startTime, LocalDateTime endTime) {
        List<CalendarAlert> alerts = calendarAlertService.findAlertsByEmailAndTimeRange(userEmail, startTime, endTime);
        return alerts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CalendarAlertResponse mapToResponse(CalendarAlert alert) {
        return CalendarAlertResponse.builder()
                .id(alert.getId())
                .userEmail(alert.getUserEmail())
                .alertAddress(alert.getAlertAddress())
                .note(alert.getNote())
                .alertTime(alert.getAlertTime())
                .status(alert.getStatus())
                .createdAt(alert.getCreatedAt())
                .updatedAt(alert.getUpdatedAt())
                .build();
    }
}
