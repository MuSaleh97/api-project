package com.spring_api.back_end.data.repository;

import com.spring_api.back_end.data.entity.CalendarAlert;
import com.spring_api.back_end.data.enums.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CalendarAlertRepository extends JpaRepository<CalendarAlert, Long> {

    List<CalendarAlert> findByUserEmailAndIsDeletedOrderByAlertTimeDesc(String userEmail, boolean isDeleted);

    List<CalendarAlert> findByUserEmailAndStatusAndIsDeletedOrderByAlertTimeDesc(String userEmail, AlertStatus status, boolean isDeleted);

    Optional<CalendarAlert> findByIdAndUserEmailAndIsDeleted(Long id, String userEmail, boolean isDeleted);

    @Query("SELECT ca FROM CalendarAlert ca WHERE ca.alertTime <= :currentTime AND ca.status = :status AND ca.isDeleted = false")
    List<CalendarAlert> findActiveAlertsDueForNotification(@Param("currentTime") LocalDateTime currentTime, @Param("status") AlertStatus status);

    @Query("SELECT ca FROM CalendarAlert ca WHERE ca.userEmail = :email AND ca.alertTime BETWEEN :startTime AND :endTime AND ca.isDeleted = false ORDER BY ca.alertTime ASC")
    List<CalendarAlert> findAlertsByEmailAndTimeRange(@Param("email") String email, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
