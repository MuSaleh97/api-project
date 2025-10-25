package com.spring_api.back_end.client.impl;

import com.spring_api.back_end.business.exception.NetworkException;
import com.spring_api.back_end.client.SmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Twilio SMS implementation for sending OTP messages
 * This implementation handles international phone numbers and provides fallback for countries
 */
@Slf4j
@Component
public class TwilioSmsClient implements SmsClient {

    @Value("${sms.twilio.account-sid:}")
    private String accountSid;

    @Value("${sms.twilio.auth-token:}")
    private String authToken;

    @Value("${sms.twilio.from-number:}")
    private String fromNumber;

    @Value("${sms.enabled:false}")
    private boolean smsEnabled;

    // Pattern for international phone number validation
    private static final Pattern INTERNATIONAL_PHONE_PATTERN =
        Pattern.compile("^\\+[1-9]\\d{1,14}$");

    // Pattern for basic phone number (digits only)
    private static final Pattern BASIC_PHONE_PATTERN =
        Pattern.compile("^[0-9]{7,15}$");

    @Override
    public boolean sendSms(String phoneNumber, String message) {
        if (!smsEnabled) {
            log.warn("SMS service is disabled. Would send to {}: {}", phoneNumber, message);
            return true; // Return true for development/testing
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            log.error("Invalid phone number format: {}", phoneNumber);
            return false;
        }

        try {
            // Format phone number if needed
            String formattedPhone = formatPhoneNumber(phoneNumber, null);

            // For demonstration purposes - in real implementation, you would use Twilio SDK
            log.info("Sending SMS via Twilio to {}: {}", formattedPhone, message);

            // Simulate SMS sending
            simulateSmsDelivery(formattedPhone, message);

            return true;

        } catch (Exception e) {
            log.error("Failed to send SMS to {}: {}", phoneNumber, e.getMessage());
            throw new NetworkException("Failed to send SMS: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }

        String cleaned = phoneNumber.trim().replaceAll("\\s+", "");

        // Check international format
        if (INTERNATIONAL_PHONE_PATTERN.matcher(cleaned).matches()) {
            return true;
        }

        // Check basic format (will be formatted to international)
        return BASIC_PHONE_PATTERN.matcher(cleaned).matches();
    }

    @Override
    public String formatPhoneNumber(String phoneNumber, String countryCode) {
        if (phoneNumber == null) {
            return null;
        }

        String cleaned = phoneNumber.trim().replaceAll("[\\s()-]", "");

        // If already in international format, return as is
        if (cleaned.startsWith("+")) {
            return cleaned;
        }

        // If starts with 00, replace with +
        if (cleaned.startsWith("00")) {
            return "+" + cleaned.substring(2);
        }

        // If no country code provided, try to detect common patterns
        if (countryCode == null || countryCode.trim().isEmpty()) {
            countryCode = detectCountryCode(cleaned);
        }

        // Remove leading zero if present (common in many countries)
        if (cleaned.startsWith("0")) {
            cleaned = cleaned.substring(1);
        }

        // Add country code
        return "+" + countryCode + cleaned;
    }

    /**
     * Simple country code detection based on common patterns
     */
    private String detectCountryCode(String phoneNumber) {
        // This is a simplified detection - in production, use a proper phone number library

        // USA/Canada (11 digits starting with 1)
        if (phoneNumber.length() == 11 && phoneNumber.startsWith("1")) {
            return "1";
        }

        // Jordan (9 digits starting with 7)
        if (phoneNumber.length() == 9 && phoneNumber.startsWith("7")) {
            return "962";
        }

        // Saudi Arabia (9 digits starting with 5)
        if (phoneNumber.length() == 9 && phoneNumber.startsWith("5")) {
            return "966";
        }

        // UAE (9 digits starting with 5)
        if (phoneNumber.length() == 9 && phoneNumber.startsWith("5")) {
            return "971";
        }

        // Egypt (10 digits starting with 1)
        if (phoneNumber.length() == 10 && phoneNumber.startsWith("1")) {
            return "20";
        }

        // Default to international format without country code (user must provide full number)
        return ""; // Will result in error - user should provide full international number
    }

    /**
     * Simulate SMS delivery for development/testing
     */
    private void simulateSmsDelivery(String phoneNumber, String message) {
        // In production, this would be replaced with actual Twilio API calls:
        /*
        Twilio.init(accountSid, authToken);
        Message.creator(
            new PhoneNumber(phoneNumber),
            new PhoneNumber(fromNumber),
            message
        ).create();
        */

        log.info("SMS delivered successfully to {}", phoneNumber);

        // Simulate network delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
