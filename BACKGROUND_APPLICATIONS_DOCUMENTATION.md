# Background Applications Documentation

## Project Overview

**Msar Al Quran** is a comprehensive Islamic application backend built with Spring Boot 3.2.4 that provides Quranic verses, hadiths, supplications, remembrances, and key Islamic principles. The system includes sophisticated background processing capabilities for notification management, SMS services, and prayer time integration.

## Architecture Overview

The application follows a layered architecture pattern with the following key components:

- **Presentation Layer**: REST Controllers and Web Controllers
- **Business Layer**: Services and Facades
- **Data Layer**: Entities, Repositories, and DTOs
- **Integration Layer**: External API clients (AlAdhan, SMS)
- **Background Processing**: Scheduled services and async operations

## Background Applications and Services

### 1. Notification Scheduler Service

**Purpose**: Manages automated calendar alert notifications with precise timing and reliable delivery.

**Key Features**:
- **Scheduled Processing**: Runs every minute (`@Scheduled(fixedRate = 60000)`) to check for due alerts
- **Alert Detection**: Queries for active alerts that have reached their scheduled notification time
- **Automatic Notification Creation**: Converts calendar alerts into user notifications
- **Status Management**: Updates alert status from ACTIVE to COMPLETED after processing
- **Error Handling**: Comprehensive logging and exception handling for failed notifications

**Background Processing Flow**:
```
1. Every 60 seconds → Check for due alerts
2. Find alerts where alertTime <= currentTime AND status = ACTIVE
3. For each due alert:
   - Create notification message
   - Save notification to database
   - Update alert status to COMPLETED
   - Log processing status
```

**Cleanup Operations**:
- **Daily Cleanup**: Runs at 1 AM (`@Scheduled(cron = "0 0 1 * * ?")`)
- **Expired Alert Handling**: Marks alerts older than 30 days as EXPIRED
- **Database Maintenance**: Prevents database bloat from old alert records

### 2. SMS Background Service (Twilio Integration)

**Purpose**: Handles SMS OTP delivery for user authentication and verification.

**Key Features**:
- **International Phone Number Support**: Handles multiple country formats
- **Phone Number Validation**: Regex-based validation for international formats
- **Auto-formatting**: Converts various phone number formats to international standard
- **Country Code Detection**: Intelligent detection for common Middle Eastern countries
- **Fallback Mechanism**: Graceful degradation when SMS service is disabled

**Supported Countries**:
- Jordan (+962)
- Saudi Arabia (+966)
- UAE (+971)
- Egypt (+20)
- USA/Canada (+1)

**Background Processing**:
- **Asynchronous Delivery**: SMS sending happens in background threads
- **Retry Logic**: Built-in error handling and retry mechanisms
- **Simulation Mode**: Development-friendly testing without actual SMS charges
- **Network Exception Handling**: Robust error management for network failures

### 3. Calendar Alert Management System

**Purpose**: Comprehensive alert lifecycle management with background processing.

**Entity Structure**:
```java
CalendarAlert {
    - id: Long
    - userEmail: String
    - alertAddress: String
    - note: String (TEXT)
    - alertTime: LocalDateTime
    - status: AlertStatus (ACTIVE, COMPLETED, CANCELLED, EXPIRED)
    - createdAt: LocalDateTime
    - updatedAt: LocalDateTime
    - isDeleted: boolean
}
```

**Background Operations**:
- **Automatic Status Updates**: Lifecycle management through status transitions
- **Soft Delete Implementation**: Logical deletion preserving data integrity
- **Time-based Queries**: Efficient querying for time-sensitive operations
- **Batch Processing**: Handles multiple alerts simultaneously

### 4. Notification Management System

**Purpose**: Centralized notification handling with real-time status tracking.

**Entity Structure**:
```java
Notification {
    - id: Long
    - email: String
    - message: String (TEXT)
    - status: NotificationStatus (READ, UNREAD)
    - calendarAlert: CalendarAlert (ManyToOne)
    - createdAt: LocalDateTime
    - readAt: LocalDateTime
    - isDeleted: boolean
}
```

**Background Features**:
- **Auto-status Management**: Default UNREAD status with timestamp tracking
- **Read State Tracking**: Automatic timestamp recording when notifications are read
- **Cascade Relationships**: Proper linking with calendar alerts
- **Batch Queries**: Optimized queries for notification lists and counts

### 5. External API Integration (AlAdhan Prayer Times)

**Purpose**: Real-time Islamic prayer time data integration.

**Background Processing**:
- **OpenFeign Client**: Declarative REST client for external API calls
- **City-based Queries**: Prayer times by city and country
- **Caching Strategy**: Efficient data retrieval and storage
- **Fallback Mechanisms**: Error handling for API unavailability

**Integration Points**:
```java
@FeignClient(name = "alAdhanClient", url = "${al.adhan.url}")
AlAdhanClient {
    - getTimingsByCity(city, country): PrayerTimesResponse
}
```

### 6. Database Background Operations

**Technology Stack**:
- **PostgreSQL**: Primary database with connection pooling
- **Hibernate/JPA**: ORM with automatic DDL updates
- **Connection Management**: Neon.tech cloud database integration

**Background Database Features**:
- **Automatic Schema Updates**: `spring.jpa.hibernate.ddl-auto=update`
- **Connection Pooling**: Efficient database connection management
- **Transaction Management**: Automatic transaction handling
- **Query Optimization**: Hibernate query optimization and caching

### 7. Email Service Background Processing

**Purpose**: Automated email delivery for OTP and notifications.

**Configuration**:
- **SMTP Integration**: Gmail SMTP with TLS encryption
- **Template Engine**: Thymeleaf for HTML email templates
- **Background Delivery**: Asynchronous email processing
- **Error Handling**: Comprehensive email delivery error management

**Templates Available**:
- `email-otp.html`: OTP delivery template
- `forgot-password.html`: Password reset template
- `verify-account.html`: Account verification template

### 8. Application Configuration and Startup

**Spring Boot Configuration**:
```java
@SpringBootApplication
@EnableFeignClients    // External API integration
@EnableAsync          // Asynchronous processing
@EnableScheduling     // Background scheduled tasks
```

**Key Background Enablers**:
- **Async Processing**: `@EnableAsync` for non-blocking operations
- **Scheduled Tasks**: `@EnableScheduling` for time-based background jobs
- **Feign Clients**: `@EnableFeignClients` for external API integration
- **Auto-configuration**: Spring Boot's auto-configuration for seamless setup

## Background Processing Patterns

### 1. Scheduled Processing Pattern
```java
@Scheduled(fixedRate = 60000) // Every minute
@Scheduled(cron = "0 0 1 * * ?") // Daily at 1 AM
```

### 2. Asynchronous Processing Pattern
```java
@Async
public CompletableFuture<Void> processInBackground()
```

### 3. Event-driven Processing Pattern
```java
@EventListener
public void handleApplicationEvent(CustomEvent event)
```

### 4. Repository Query Optimization
```java
@Query("SELECT ca FROM CalendarAlert ca WHERE ca.alertTime <= :currentTime")
List<CalendarAlert> findActiveAlertsDueForNotification()
```

## Configuration Management

### Environment-based Configuration
```properties
# Production SMS Configuration
sms.enabled=${SMS_ENABLED:true}
sms.twilio.account-sid=${TWILIO_ACCOUNT_SID}
sms.twilio.auth-token=${TWILIO_AUTH_TOKEN}

# Database Configuration
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}

# Email Configuration
spring.mail.username=${EMAIL_USERNAME}
spring.mail.password=${EMAIL_PASSWORD}
```

### Feature Toggles
- **SMS Service**: Can be enabled/disabled via configuration
- **Development Mode**: Special handling for testing environments
- **Database DDL**: Configurable schema management
- **Logging Levels**: Environment-specific logging configuration

## Performance Optimization

### Background Processing Optimizations
1. **Batch Processing**: Multiple alerts processed in single operations
2. **Efficient Queries**: Indexed queries for time-based operations
3. **Connection Pooling**: Database connection optimization
4. **Lazy Loading**: JPA lazy loading for related entities
5. **Caching**: Strategic caching for frequently accessed data

### Monitoring and Logging
```java
@Slf4j
public class NotificationSchedulerService {
    log.info("Processing alerts at: {}", LocalDateTime.now());
    log.error("Error processing alert ID: {}", alert.getId(), e);
}
```

## Error Handling and Resilience

### Exception Management
- **Custom Exceptions**: Domain-specific exception types
- **Global Exception Handler**: Centralized error handling
- **Retry Logic**: Automatic retry for transient failures
- **Circuit Breaker**: Protection against cascading failures

### Data Integrity
- **Soft Deletes**: Logical deletion preserving audit trails
- **Transaction Management**: ACID compliance for critical operations
- **Validation**: Input validation at multiple layers
- **Audit Trails**: Timestamp tracking for all operations

## Security Considerations

### Background Process Security
- **Environment Variables**: Sensitive configuration via environment
- **Database Security**: Encrypted connections and credentials
- **API Security**: Secure external API communication
- **Input Validation**: Sanitization of all background processing inputs

## Deployment and DevOps

### Containerization
```dockerfile
FROM openjdk:21-jdk
COPY target/back-end-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Background Service Monitoring
- **Health Checks**: Spring Boot Actuator endpoints
- **Metrics Collection**: Application performance monitoring
- **Log Aggregation**: Centralized logging for background processes
- **Alert Configuration**: Monitoring for failed background jobs

## Future Enhancements

### Planned Background Improvements
1. **Message Queue Integration**: Redis/RabbitMQ for reliable message processing
2. **Distributed Scheduling**: Quartz scheduler for clustered environments
3. **Advanced Caching**: Redis integration for improved performance
4. **Real-time Notifications**: WebSocket integration for instant notifications
5. **Analytics Processing**: Background analytics data processing
6. **Machine Learning**: Background ML processing for personalized recommendations

## Conclusion

The Msar Al Quran backend application demonstrates sophisticated background processing capabilities essential for a modern Islamic application. The combination of scheduled tasks, asynchronous processing, external API integration, and robust data management creates a reliable and scalable foundation for serving Islamic content and managing user interactions.

The background applications ensure:
- **Reliable Notification Delivery**: Time-sensitive alerts reach users precisely when needed
- **Seamless SMS Integration**: Multi-country OTP delivery with intelligent formatting
- **Efficient Data Management**: Automated cleanup and lifecycle management
- **External Service Integration**: Reliable prayer time data and communication services
- **Scalable Architecture**: Ready for horizontal scaling and cloud deployment

This comprehensive background processing framework positions the application for reliable operation in production environments while maintaining excellent user experience and data integrity.
