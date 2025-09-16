package com.spring_api.back_end.business.exception;

import com.spring_api.back_end.data.dto.ErrorMessage;
import com.spring_api.back_end.data.enums.ErrorMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidation(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorMessage> handleLoginException(LoginException ex) {
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.LOGIN_ERROR.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.LOGIN_ERROR.getArDescription());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    @ExceptionHandler(PasswordsMatchException.class)
    public ResponseEntity<ErrorMessage> handlePasswordsMatchException(PasswordsMatchException ex) {
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.PASSWORD_DO_NOT_MATCH.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.PASSWORD_DO_NOT_MATCH.getArDescription());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    @ExceptionHandler(EmailUniqueException.class)
    public ResponseEntity<ErrorMessage> handleEmailUniqueEmailUnique(EmailUniqueException ex) {
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.EMAIL_ALREADY_USED.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.EMAIL_ALREADY_USED.getArDescription());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    @ExceptionHandler(PhoneUniqueException.class)
    public ResponseEntity<ErrorMessage> handlePhoneUniqueEmailUnique(PhoneUniqueException ex) {
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.MOBILE_NUMBER_ALREADY_USED.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.MOBILE_NUMBER_ALREADY_USED.getArDescription());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    @ExceptionHandler(DeleteAccountException.class)
    public ResponseEntity<ErrorMessage> handlePhoneUniqueEmailUnique(DeleteAccountException ex) {
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.ACCOUNT_ALREADY_DELETE.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.ACCOUNT_ALREADY_DELETE.getArDescription());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleAccountNotFoundException(AccountNotFoundException ex) {
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.ACCOUNT_NOT_FOUND.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.ACCOUNT_NOT_FOUND.getArDescription());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    @ExceptionHandler(OTPSendErrorException.class)
    public ResponseEntity<ErrorMessage> handleOTPSendError(OTPSendErrorException ex) {
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.OTP_SEND_ERROR.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.OTP_SEND_ERROR.getArDescription());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    @ExceptionHandler(OTPVerificationException.class)
    public ResponseEntity<ErrorMessage> handleOTPVerificationException(OTPVerificationException ex) {
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.CHECK_OTP.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.CHECK_OTP.getArDescription());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    @ExceptionHandler(ChangeEmailException.class)
    public ResponseEntity<ErrorMessage> handleChangeEmailException(ChangeEmailException ex) {
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.CHANGE_EMAIL.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.CHANGE_EMAIL.getArDescription());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    @ExceptionHandler(AccountIsNotVerifiedException.class)
    public ResponseEntity<ErrorMessage> handleAccountIIsNotVerifiedException(AccountIsNotVerifiedException ex) {
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setEnDescription(ErrorMsg.IS_NOT_VERIFIED.getEnDescription());
        errorMessage.setArDescription(ErrorMsg.IS_NOT_VERIFIED.getArDescription());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }
}
