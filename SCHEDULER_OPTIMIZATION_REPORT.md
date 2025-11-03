# Notification Scheduler Optimization and Configuration Fixes

## Issues Identified and Fixed

### 1. Port Configuration Issue
- **Problem**: Application properties showed port 81000, but logs showed port 10000
- **Fix**: Corrected `server.port=10000` in application.properties to match actual running port

### 2. Scheduler Performance Optimization
- **Problem**: Scheduler was running every minute (60000ms) causing frequent database queries
- **Fix**: 
  - Changed frequency to every 5 minutes (300000ms) to reduce database load
  - Added configurable scheduler properties
  - Added initial delay to prevent immediate execution on startup

### 3. Database Query Optimization
- **Problem**: Frequent full queries even when no alerts were due
- **Fix**:
  - Added `countActiveAlertsDueForNotification()` method to check if alerts exist before fetching
  - Optimized query with ORDER BY for better performance
  - Added proper database connection pooling configuration

### 4. Logging Improvements
- **Problem**: Too verbose logging causing log spam when no alerts are due
- **Fix**:
  - Changed frequent "Processing alerts" message to debug level
  - Only log at INFO level when alerts are actually found
  - Maintained proper error logging

### 5. Configuration Management
- **Problem**: Hard-coded scheduler values
- **Fix**:
  - Added configurable properties for scheduler behavior
  - Added conditional scheduling based on configuration
  - Made the scheduler easily disableable via properties

## Configuration Properties Added

```properties
# Notification Scheduler Configuration
notification.scheduler.enabled=true
notification.scheduler.fixed-rate=300000
notification.scheduler.initial-delay=60000

# Database Connection Pool Configuration
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000
spring.datasource.hikari.connection-timeout=30000
```

## Performance Improvements

1. **Reduced Database Load**: Scheduler now runs every 5 minutes instead of every minute (83% reduction in query frequency)
2. **Smart Querying**: Count check before full data fetch reduces unnecessary data transfer
3. **Connection Pooling**: Proper database connection management
4. **Conditional Processing**: Scheduler can be disabled via configuration for testing/maintenance

## Production Recommendations

1. **Adjust Scheduler Frequency**: Consider increasing to 10-15 minutes for production based on business requirements
2. **Database Indexing**: Ensure indexes on `alert_time`, `status`, and `is_deleted` columns
3. **Monitoring**: Add metrics to track scheduler performance and alert processing times
4. **Batch Processing**: Consider processing alerts in batches if volume increases

## Testing

The configuration now supports easy testing by setting:
```properties
notification.scheduler.enabled=false
```

This will disable the scheduler completely during testing.
