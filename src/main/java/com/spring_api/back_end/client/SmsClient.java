package com.spring_api.back_end.client;

/**
 * Interface for SMS service providers to send OTP messages
 */
public interface SmsClient {

    /**
     * Send SMS OTP to the specified phone number
     * @param phoneNumber The phone number in international format (e.g., +1234567890)
     * @param message The OTP message to send
     * @return true if SMS was sent successfully, false otherwise
     */
    boolean sendSms(String phoneNumber, String message);

    /**
     * Validate if the phone number format is correct for SMS sending
     * @param phoneNumber The phone number to validate
     * @return true if phone number is valid, false otherwise
     */
    boolean isValidPhoneNumber(String phoneNumber);

    /**
     * Format phone number to international format
     * @param phoneNumber The phone number to format
     * @param countryCode The country code (optional, can be null for auto-detection)
     * @return Formatted phone number in international format
     */
    String formatPhoneNumber(String phoneNumber, String countryCode);
}
