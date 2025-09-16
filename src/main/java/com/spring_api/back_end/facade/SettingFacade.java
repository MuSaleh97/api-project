package com.spring_api.back_end.facade;

import com.spring_api.back_end.business.exception.ChangeEmailException;
import com.spring_api.back_end.business.exception.LoginException;
import com.spring_api.back_end.business.exception.PasswordsMatchException;
import com.spring_api.back_end.business.service.AccountService;
import com.spring_api.back_end.data.dto.request.ChangePasswordRequest;
import com.spring_api.back_end.data.dto.request.ForgotPasswordRequest;
import com.spring_api.back_end.data.dto.request.ProfileDataRequest;
import com.spring_api.back_end.data.dto.response.AccountResponse;
import com.spring_api.back_end.data.entity.Account;
import com.spring_api.back_end.util.AESUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SettingFacade {

    private final AccountService service;
    private final OtpEmailFacade otpEmailFacade;

    public AccountResponse changePassword(ChangePasswordRequest request) throws Exception {
        Account account = findAccountByEmailOrPhone(request.getEmailOrPhone());
        validatePassword(AESUtil.decrypt(account.getPassword(), account.getSecretKey()), request.getOldPassword());
        validatePasswordsMatch(request.getNewPassword(), request.getConfirmNewPassword());

        account.setPassword(AESUtil.encrypt(request.getNewPassword(), account.getSecretKey()));
        Account saved = service.save(account);
        return toResponse(saved);
    }

    public AccountResponse forgotPassword(ForgotPasswordRequest request) throws Exception {
        Account account = findAccountByEmailOrPhone(request.getEmailOrPhone());
        validatePasswordsMatch(request.getNewPassword(), request.getConfirmNewPassword());

        account.setPassword(AESUtil.encrypt(request.getNewPassword(), account.getSecretKey()));
        Account saved = service.save(account);
        return toResponse(saved);
    }

    public AccountResponse updateProfile(ProfileDataRequest profileDataRequest) throws IOException {
        Account account = findAccountByEmailOrPhone(profileDataRequest.getOldEmail());

        account.setFullName(profileDataRequest.getNewFullName());
        account.setPhone(profileDataRequest.getNewPhone());
        account.setDob(profileDataRequest.getNewDob());
        account.setGender(profileDataRequest.getNewGender());
        if(!account.getEmail().equalsIgnoreCase(profileDataRequest.getNewEmail())){
            account.setEmail(profileDataRequest.getNewEmail());
            account.setVerifyEmail(false);
            service.save(account);
            otpEmailFacade.sendOtp(profileDataRequest.getNewEmail());
            throw new ChangeEmailException();
        }
        service.save(account);
        return toResponse(account);
    }

    private Account findAccountByEmailOrPhone(String emailOrPhone) {
        Optional<Account> optional = emailOrPhone.contains("@")
                ? service.findByEmailAndIsDeleted(emailOrPhone, false)
                : service.findByPhoneAndIsDeleted(emailOrPhone, false);

        return optional.orElseThrow(LoginException::new);
    }

    private void validatePassword(String actual, String provided) {
        if (!actual.equals(provided)) {
            throw new LoginException();
        }
    }

    private void validatePasswordsMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new PasswordsMatchException();
        }
    }

    private AccountResponse toResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .fullName(account.getFullName())
                .email(account.getEmail())
                .phone(account.getPhone())
                .dob(account.getDob())
                .gender(account.getGender())
                .isDeleted(account.isDeleted())
                .isActive(account.isActive())
                .build();
    }
}
