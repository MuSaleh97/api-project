package com.spring_api.back_end.controller;

import com.spring_api.back_end.data.dto.request.AddAlertRequest;
import com.spring_api.back_end.data.dto.request.DeleteAlertRequest;
import com.spring_api.back_end.data.dto.request.UpdateAlertRequest;
import com.spring_api.back_end.data.dto.response.CalendarAlertResponse;
import com.spring_api.back_end.data.enums.AlertStatus;
import com.spring_api.back_end.facade.CalendarAlertFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/calendar-alerts")
@RequiredArgsConstructor
@Tag(name = "Calendar Alert Management", description = "APIs for managing calendar alerts")
public class CalendarAlertController {

    private final CalendarAlertFacade calendarAlertFacade;

    @PostMapping("/add")
    @Operation(summary = "Add new calendar alert", description = "Creates a new calendar alert for the user")
    public ResponseEntity<CalendarAlertResponse> addAlert(@Valid @RequestBody AddAlertRequest request) {
        try {
            CalendarAlertResponse response = calendarAlertFacade.addAlert(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update")
    @Operation(summary = "Update calendar alert", description = "Updates an existing calendar alert")
    public ResponseEntity<CalendarAlertResponse> updateAlert(
            @Valid @RequestBody UpdateAlertRequest request,
            @RequestParam String userEmail) {
        try {
            CalendarAlertResponse response = calendarAlertFacade.updateAlert(request, userEmail);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete calendar alert", description = "Deletes a calendar alert")
    public ResponseEntity<Void> deleteAlert(
            @Valid @RequestBody DeleteAlertRequest request,
            @RequestParam String userEmail) {
        try {
            calendarAlertFacade.deleteAlert(request, userEmail);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/user/{userEmail}")
    @Operation(summary = "Get user alerts", description = "Retrieves all alerts for a specific user")
    public ResponseEntity<List<CalendarAlertResponse>> getUserAlerts(@PathVariable String userEmail) {
        try {
            List<CalendarAlertResponse> alerts = calendarAlertFacade.getUserAlerts(userEmail);
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/{userEmail}/status/{status}")
    @Operation(summary = "Get user alerts by status", description = "Retrieves alerts by status for a specific user")
    public ResponseEntity<List<CalendarAlertResponse>> getUserAlertsByStatus(
            @PathVariable String userEmail,
            @PathVariable AlertStatus status) {
        try {
            List<CalendarAlertResponse> alerts = calendarAlertFacade.getUserAlertsByStatus(userEmail, status);
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get alert by ID", description = "Retrieves a specific alert by ID")
    public ResponseEntity<CalendarAlertResponse> getAlertById(
            @PathVariable Long id,
            @RequestParam String userEmail) {
        try {
            CalendarAlertResponse alert = calendarAlertFacade.getAlertById(id, userEmail);
            return ResponseEntity.ok(alert);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/user/{userEmail}/range")
    @Operation(summary = "Get alerts in time range", description = "Retrieves alerts within a specific time range")
    public ResponseEntity<List<CalendarAlertResponse>> getAlertsInTimeRange(
            @PathVariable String userEmail,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        try {
            List<CalendarAlertResponse> alerts = calendarAlertFacade.getAlertsInTimeRange(userEmail, startTime, endTime);
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
