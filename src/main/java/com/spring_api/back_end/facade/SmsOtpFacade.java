package com.spring_api.back_end.facade;

import com.spring_api.back_end.business.exception.*;
import com.spring_api.back_end.business.service.AccountService;
import com.spring_api.back_end.business.service.SmsOtpService;
import com.spring_api.back_end.client.SmsClient;
import com.spring_api.back_end.data.entity.Account;
import com.spring_api.back_end.data.entity.OtpPhone;
import com.spring_api.back_end.util.PhoneUtil;
import com.spring_api.back_end.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsOtpFacade {

    private final AccountService accountService;
    private final SmsOtpService smsOtpService;
    private final SmsClient smsClient;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int OTP_VALIDITY_MINUTES = 5;
    private static final int OTP_LENGTH = 6;

    /**
     * Send OTP to phone number
     * @param phone The phone number (can be local or international format)
     * @param countryCode Optional country code for local numbers
     * @return Success message
     */
    public String sendOtpToPhone(String phone, String countryCode) {
        // Validate input
        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidRequestDataException("Phone number is required");
        }

        // Clean and validate phone number
        String cleanedPhone = PhoneUtil.cleanPhoneNumber(phone);
        if (!PhoneUtil.isValidPhoneNumber(cleanedPhone)) {
            throw new InvalidRequestDataException("Invalid phone number format: " + phone);
        }

        // Format to international format
        String internationalPhone;
        try {
            internationalPhone = PhoneUtil.formatToInternational(cleanedPhone, countryCode);
            if (internationalPhone == null || !internationalPhone.startsWith("+")) {
                throw new InvalidRequestDataException("Unable to format phone number. Please provide full international number (e.g., +962791234567)");
            }
        } catch (Exception e) {
            throw new InvalidRequestDataException("Invalid phone number format. Please provide a valid international number (e.g., +962791234567)");
        }

        // Check if account exists with this phone number
        Optional<Account> accountOpt = accountService.findByPhoneAndIsDeleted(internationalPhone, false);
        if (accountOpt.isEmpty()) {
            throw new AccountNotFoundException("No account found with phone number: " + internationalPhone);
        }

        Account account = accountOpt.get();

        // Generate OTP
        String otp = generateOtp();

        // Create and save OTP record
        OtpPhone otpPhone = createOtpPhoneObject(account, internationalPhone, otp);
        smsOtpService.saveOtpPhone(otpPhone);

        // Send SMS
        String message = createOtpMessage(otp);
        boolean smsSent = smsClient.sendSms(internationalPhone, message);

        if (!smsSent) {
            throw new OTPSendErrorException("Failed to send SMS to " + internationalPhone);
        }

        log.info("OTP sent successfully to phone: {}", internationalPhone);
        return "OTP sent to: " + maskPhoneNumber(internationalPhone);
    }

    /**
     * Verify OTP for phone number
     * @param phone The phone number
     * @param otp The OTP code
     * @param countryCode Optional country code for local numbers
     * @return true if verification successful
     */
    public boolean verifyPhoneOtp(String phone, String otp, String countryCode) {
        // Validate input
        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidRequestDataException("Phone number is required");
        }
        if (otp == null || otp.trim().isEmpty()) {
            throw new InvalidRequestDataException("OTP is required");
        }

        // Format phone number
        String cleanedPhone = PhoneUtil.cleanPhoneNumber(phone);
        String internationalPhone;
        try {
            internationalPhone = PhoneUtil.formatToInternational(cleanedPhone, countryCode);
            if (internationalPhone == null || !internationalPhone.startsWith("+")) {
                throw new InvalidRequestDataException("Unable to format phone number. Please provide full international number");
            }
        } catch (Exception e) {
            throw new InvalidRequestDataException("Invalid phone number format");
        }

        // Find account
        Optional<Account> accountOpt = accountService.findByPhoneAndIsDeleted(internationalPhone, false);
        if (accountOpt.isEmpty()) {
            throw new AccountNotFoundException("No account found with phone number: " + internationalPhone);
        }

        // Find latest OTP
        Optional<OtpPhone> otpPhoneOpt = smsOtpService.getLatestOtpByPhone(internationalPhone);
        if (otpPhoneOpt.isEmpty()) {
            throw new OTPVerificationException("No OTP found for this phone number. Please request a new OTP.");
        }

        OtpPhone otpPhone = otpPhoneOpt.get();

        // Check if OTP is expired
        Instant now = Instant.now();
        Instant otpTime = otpPhone.getOtpTimestamp().toInstant();
        if (otpTime.isBefore(now.minus(OTP_VALIDITY_MINUTES, ChronoUnit.MINUTES))) {
            throw new OTPVerificationException("OTP has expired. Please request a new OTP.");
        }

        // Verify OTP
        if (!otp.trim().equals(otpPhone.getOtp())) {
            throw new OTPVerificationException("Invalid OTP. Please check the code and try again.");
        }

        // Update account verification status
        Account account = accountOpt.get();
        account.setVerifyPhone(true); // Assuming this field exists or needs to be added
        accountService.save(account);

        // Clean up used OTP
        smsOtpService.deleteOtpPhone(otpPhone);

        log.info("Phone number verified successfully: {}", internationalPhone);
        return true;
    }

    /**
     * Generate secure OTP code
     */
    private String generateOtp() {
        int min = (int) Math.pow(10, OTP_LENGTH - 1);
        int max = (int) Math.pow(10, OTP_LENGTH) - 1;
        return String.valueOf(min + secureRandom.nextInt(max - min + 1));
    }

    /**
     * Create OTP phone object
     */
    private OtpPhone createOtpPhoneObject(Account account, String phone, String otp) {
        return OtpPhone.builder()
                .id(Util.generateOtpId())
                .idMember(account.getId())
                .phone(phone)
                .otp(otp)
                .otpTimestamp(Timestamp.from(Instant.now()))
                .build();
    }

    /**
     * Create OTP SMS message
     */
    private String createOtpMessage(String otp) {
        return String.format("""
            Your verification code is: %s
            This code will expire in %d minutes.
            Do not share this code with anyone.""",
            otp, OTP_VALIDITY_MINUTES
        );
    }

    /**
     * Mask phone number for privacy
     */
    private String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() < 4) {
            return phone;
        }

        String countryPart = phone.substring(0, phone.length() - 6);
        String maskedPart = "****";
        String lastPart = phone.substring(phone.length() - 2);

        return countryPart + maskedPart + lastPart;
    }
}
