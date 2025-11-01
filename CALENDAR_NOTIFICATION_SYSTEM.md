# Calendar Notification System Implementation

## Overview
This document describes the comprehensive calendar notification system implementation for the Spring Boot API project. The system provides professional calendar alert management with automatic notification generation.

## Database Tables

### 1. CALENDAR_ALERT Table
```sql
CREATE TABLE CALENDAR_ALERT (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    USER_EMAIL VARCHAR(255) NOT NULL,
    ALERT_ADDRESS VARCHAR(500),
    NOTE TEXT,
    ALERT_TIME DATETIME NOT NULL,
    STATUS VARCHAR(50),
    CREATED_AT DATETIME,
    UPDATED_AT DATETIME,
    IS_DELETED BOOLEAN DEFAULT FALSE
);
```

### 2. NOTIFICATION Table
```sql
CREATE TABLE NOTIFICATION (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    EMAIL VARCHAR(255) NOT NULL,
    MESSAGE TEXT NOT NULL,
    STATUS VARCHAR(50),
    ALERT_ID BIGINT,
    CREATED_AT DATETIME,
    READ_AT DATETIME,
    IS_DELETED BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (ALERT_ID) REFERENCES CALENDAR_ALERT(ID)
);
```

## Key Features Implemented

### 1. Calendar Alert Management APIs
- **Add Alert**: POST `/api/calendar-alerts/add`
- **Update Alert**: PUT `/api/calendar-alerts/update`
- **Delete Alert**: DELETE `/api/calendar-alerts/delete`
- **Get User Alerts**: GET `/api/calendar-alerts/user/{userEmail}`
- **Get Alerts by Status**: GET `/api/calendar-alerts/user/{userEmail}/status/{status}`
- **Get Alert by ID**: GET `/api/calendar-alerts/{id}`
- **Get Alerts in Time Range**: GET `/api/calendar-alerts/user/{userEmail}/range`

### 2. Notification Management APIs
- **Get Notifications**: POST `/api/notifications/get`
- **Get All User Notifications**: GET `/api/notifications/user/{email}`
- **Get Unread Notifications**: GET `/api/notifications/user/{email}/unread`
- **Get Unread Count**: GET `/api/notifications/user/{email}/count`
- **Mark as Read**: PUT `/api/notifications/{id}/read`
- **Get Notification by ID**: GET `/api/notifications/{id}`
- **Delete Notification**: DELETE `/api/notifications/{id}`

### 3. Automatic Notification System
- **Scheduler Service**: Runs every minute to check for due alerts
- **Notification Generation**: Automatically creates notifications when alerts are due
- **Alert Status Management**: Updates alert status to COMPLETED after notification
- **Cleanup Service**: Daily cleanup of expired alerts (older than 30 days)

## Architecture Components

### 1. Entities
- `CalendarAlert`: Main alert entity with user email, address, note, and time
- `Notification`: Notification entity linked to alerts with read/unread status

### 2. Enums
- `AlertStatus`: ACTIVE, COMPLETED, CANCELLED, EXPIRED
- `NotificationStatus`: READ, UNREAD

### 3. DTOs
#### Request DTOs
- `AddAlertRequest`: For creating new alerts
- `UpdateAlertRequest`: For updating existing alerts
- `DeleteAlertRequest`: For deleting alerts
- `GetNotificationsRequest`: For retrieving notifications

#### Response DTOs
- `CalendarAlertResponse`: Alert data response
- `NotificationResponse`: Notification data response

### 4. Services
- `CalendarAlertService`: Business logic for alert management
- `NotificationService`: Business logic for notification management
- `NotificationSchedulerService`: Automated notification processing

### 5. Facades
- `CalendarAlertFacade`: Alert management facade
- `NotificationFacade`: Notification management facade

### 6. Controllers
- `CalendarAlertController`: REST endpoints for alert management
- `NotificationController`: REST endpoints for notification management

## Professional Features Added

### 1. Security & Validation
- Email validation for user identification
- Request validation using Bean Validation annotations
- User-specific data access control

### 2. Data Management
- Soft delete implementation (isDeleted flag)
- Automatic timestamps (createdAt, updatedAt)
- JPA lifecycle callbacks (@PrePersist, @PreUpdate)

### 3. Error Handling
- Custom exception classes
- Proper HTTP status codes
- Runtime exception handling

### 4. Performance Optimization
- Lazy loading for relationships
- Indexed queries for efficient data retrieval
- Scheduled cleanup of old data

### 5. API Documentation
- Swagger/OpenAPI annotations
- Comprehensive endpoint documentation
- Operation descriptions

## Usage Examples

### Adding a New Alert
```json
POST /api/calendar-alerts/add
{
    "userEmail": "user@example.com",
    "alertAddress": "Conference Room A",
    "note": "Team meeting discussion",
    "alertTime": "2025-11-01T14:30:00"
}
```

### Getting User Notifications
```json
POST /api/notifications/get
{
    "email": "user@example.com",
    "status": "UNREAD"
}
```

### Marking Notification as Read
```
PUT /api/notifications/123/read?email=user@example.com
```

## Automatic Notification Processing

The system automatically:
1. Checks for due alerts every minute
2. Creates notifications for alerts that have reached their scheduled time
3. Updates alert status to COMPLETED
4. Generates user-friendly notification messages
5. Cleans up expired alerts daily

## Integration Points

The system integrates seamlessly with the existing project structure:
- Uses existing database configuration
- Follows established naming conventions
- Integrates with existing Swagger configuration
- Uses consistent error handling patterns

## Next Steps for Enhancement

1. **Email/SMS Integration**: Connect with existing email/SMS services
2. **Real-time Notifications**: WebSocket implementation for live updates
3. **Recurring Alerts**: Support for repeating calendar events
4. **Time Zone Support**: Multi-timezone alert handling
5. **Mobile Push Notifications**: Integration with mobile notification services
