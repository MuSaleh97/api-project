package com.spring_api.back_end.data.repository;

import com.spring_api.back_end.data.entity.Notification;
import com.spring_api.back_end.data.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByEmailAndIsDeletedOrderByCreatedAtDesc(String email, boolean isDeleted);

    List<Notification> findByEmailAndStatusAndIsDeletedOrderByCreatedAtDesc(String email, NotificationStatus status, boolean isDeleted);

    Optional<Notification> findByIdAndEmailAndIsDeleted(Long id, String email, boolean isDeleted);

    @Query("SELECT n FROM Notification n WHERE n.email = :email AND n.status = :status AND n.isDeleted = false")
    long countUnreadNotifications(@Param("email") String email, @Param("status") NotificationStatus status);

    @Query("SELECT n FROM Notification n WHERE n.calendarAlert.id = :alertId AND n.isDeleted = false")
    List<Notification> findByCalendarAlertId(@Param("alertId") Long alertId);
}
