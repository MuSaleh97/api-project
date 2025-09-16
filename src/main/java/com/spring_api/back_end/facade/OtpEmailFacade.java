package com.spring_api.back_end.facade;

import com.spring_api.back_end.business.exception.AccountNotFoundException;
import com.spring_api.back_end.business.exception.OTPSendErrorException;
import com.spring_api.back_end.business.exception.OTPVerificationException;
import com.spring_api.back_end.business.service.AccountService;
import com.spring_api.back_end.business.service.SendOtpService;
import com.spring_api.back_end.business.service.VerifyOtpService;
import com.spring_api.back_end.data.entity.Account;
import com.spring_api.back_end.data.entity.OtpEmail;
import com.spring_api.back_end.util.Util;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OtpEmailFacade {

    private final AccountService accountService;
    private final SendOtpService sendOtpService;
    private final VerifyOtpService verifyOtpService;

    private static final String SENDER_EMAIL = "otp.gulfstream@gmail.com";
    private static final SecureRandom secureRandom = new SecureRandom();

    private final JavaMailSender javaMailSender;

    public String sendOtp(String email) throws IOException {
        Optional<Account> accountOpt = accountService.findByEmailAndIsDeleted(email, false);

        if (accountOpt.isEmpty()) {
            throw new AccountNotFoundException();
        }

        Account account = accountOpt.get();
        String otp = generateOtp();

        OtpEmail otpEmail = createOtpEmailObject(account, otp);
        sendOtpService.saveOtpEmail(otpEmail);

        log.info("OTP email saved: {}", otpEmail);
        sendOtpEmail(email, otp);

        return "OTP sent to: " + email;
    }

    private String generateOtp() {
        return String.valueOf(1000 + secureRandom.nextInt(9000));
    }

    private OtpEmail createOtpEmailObject(Account account, String otp) {
        return OtpEmail.builder()
                .id(Util.generateOtpId())
                .idMember(account.getId())
                .email(account.getEmail())
                .otp(otp)
                .otpTimestamp(Timestamp.from(Instant.now()))
                .build();
    }

    private void sendOtpEmail(String email, String otp) throws IOException {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(SENDER_EMAIL);
        message.setTo(email);
        message.setSubject("OTP Validation");

        try (var inputStream = Objects.requireNonNull(
                getClass().getResourceAsStream("/templates/email-otp.html"))
        ) {
            String htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            htmlContent = htmlContent.replace("{{OTP_CODE}}", otp);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
            helper.setFrom(SENDER_EMAIL);
            helper.setTo(email);
            helper.setSubject("OTP Validation");
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new OTPSendErrorException();
        }
    }

    public boolean emailVerify(String email, String otp) {
        Optional<Account> optionalAccount = accountService.findByEmailAndIsDeleted(email, false);

        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException();
        }

        Optional<OtpEmail> optionalOtp = Optional.ofNullable(verifyOtpService.getLatestOtpToEmail(email));

        if (optionalOtp.isEmpty() || !otp.equals(optionalOtp.get().getOtp())) {
            throw new OTPVerificationException();
        }

        Account account = optionalAccount.get();
        account.setVerifyEmail(true);

        Account updatedAccount = accountService.save(account);
        log.info("Account after verification: {}", updatedAccount);

        return true;
    }
}
