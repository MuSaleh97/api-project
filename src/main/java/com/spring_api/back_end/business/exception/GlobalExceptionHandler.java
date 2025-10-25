package com.spring_api.back_end.business.exception;

import com.spring_api.back_end.data.dto.ErrorMessage;
import com.spring_api.back_end.data.enums.ErrorMsg;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.DateTimeException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleValidation(ValidationException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.INVALID_REQUEST_DATA.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.INVALID_REQUEST_DATA.getArDescription());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleLoginException(LoginException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.LOGIN_ERROR.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.LOGIN_ERROR.getArDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(PasswordsMatchException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handlePasswordsMatchException(PasswordsMatchException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.PASSWORD_DO_NOT_MATCH.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.PASSWORD_DO_NOT_MATCH.getArDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(EmailUniqueException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleEmailUniqueException(EmailUniqueException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.EMAIL_ALREADY_USED.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.EMAIL_ALREADY_USED.getArDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(PhoneUniqueException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handlePhoneUniqueException(PhoneUniqueException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.MOBILE_NUMBER_ALREADY_USED.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.MOBILE_NUMBER_ALREADY_USED.getArDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(DeleteAccountException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleDeleteAccountException(DeleteAccountException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.ACCOUNT_ALREADY_DELETE.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.ACCOUNT_ALREADY_DELETE.getArDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleAccountNotFoundException(AccountNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.ACCOUNT_NOT_FOUND.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.ACCOUNT_NOT_FOUND.getArDescription());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(OTPSendErrorException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleOTPSendError(OTPSendErrorException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.OTP_SEND_ERROR.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.OTP_SEND_ERROR.getArDescription());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(OTPVerificationException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleOTPVerificationException(OTPVerificationException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.CHECK_OTP.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.CHECK_OTP.getArDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(ChangeEmailException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleChangeEmailException(ChangeEmailException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.CHANGE_EMAIL.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.CHANGE_EMAIL.getArDescription());
        return ResponseEntity.status(HttpStatus.OK).body(errorMessage);
    }

    @ExceptionHandler(AccountIsNotVerifiedException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleAccountIsNotVerifiedException(AccountIsNotVerifiedException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.IS_NOT_VERIFIED.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.IS_NOT_VERIFIED.getArDescription());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }

    // New exception handlers for additional error scenarios
    @ExceptionHandler(InvalidLocationException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleInvalidLocationException(InvalidLocationException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.INVALID_LOCATION.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.INVALID_LOCATION.getArDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidZoneRegionException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleInvalidZoneRegionException(InvalidZoneRegionException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.INVALID_ZONE_REGION.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.INVALID_ZONE_REGION.getArDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(ExternalApiException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleExternalApiException(ExternalApiException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.EXTERNAL_API_ERROR.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.EXTERNAL_API_ERROR.getArDescription());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorMessage);
    }

    @ExceptionHandler(InvalidPrayerTimeFormatException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleInvalidPrayerTimeFormatException(InvalidPrayerTimeFormatException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.INVALID_PRAYER_TIME_FORMAT.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.INVALID_PRAYER_TIME_FORMAT.getArDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(NetworkException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleNetworkException(NetworkException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.NETWORK_ERROR.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.NETWORK_ERROR.getArDescription());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorMessage);
    }

    @ExceptionHandler(InvalidRequestDataException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleInvalidRequestDataException(InvalidRequestDataException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.INVALID_REQUEST_DATA.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.INVALID_REQUEST_DATA.getArDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(PrayerTimeCalculationException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handlePrayerTimeCalculationException(PrayerTimeCalculationException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.PRAYER_TIME_CALCULATION_ERROR.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.PRAYER_TIME_CALCULATION_ERROR.getArDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    // Handle Feign client exceptions (for external API calls)
    @ExceptionHandler(FeignException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleFeignException(FeignException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        if (ex.status() >= 400 && ex.status() < 500) {
            errorMessage.setEnDescription(ErrorMsg.INVALID_LOCATION.getEnDescription());
            errorMessage.setArDescription(ErrorMsg.INVALID_LOCATION.getArDescription());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } else {
            errorMessage.setEnDescription(ErrorMsg.EXTERNAL_API_ERROR.getEnDescription());
            errorMessage.setArDescription(ErrorMsg.EXTERNAL_API_ERROR.getArDescription());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorMessage);
        }
    }

    // Handle date/time parsing exceptions
    @ExceptionHandler(DateTimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleDateTimeException(DateTimeException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.INVALID_DATE_TIME.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.INVALID_DATE_TIME.getArDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    // Handle IllegalArgumentException (for invalid zones, etc.)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        if (ex.getMessage() != null && ex.getMessage().contains("zone")) {
            errorMessage.setEnDescription(ErrorMsg.INVALID_ZONE_REGION.getEnDescription());
            errorMessage.setArDescription(ErrorMsg.INVALID_ZONE_REGION.getArDescription());
        } else {
            errorMessage.setEnDescription(ErrorMsg.INVALID_REQUEST_DATA.getEnDescription());
            errorMessage.setArDescription(ErrorMsg.INVALID_REQUEST_DATA.getArDescription());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    // Handle NullPointerException
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleNullPointerException(NullPointerException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.MISSING_REQUIRED_FIELD.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.MISSING_REQUIRED_FIELD.getArDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    // Handle general exceptions
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleGeneralException(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.INTERNAL_SERVER_ERROR.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.INTERNAL_SERVER_ERROR.getArDescription());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
