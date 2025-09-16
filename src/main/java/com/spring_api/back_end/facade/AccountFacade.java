package com.spring_api.back_end.facade;

import com.spring_api.back_end.business.exception.*;
import com.spring_api.back_end.business.service.AccountService;
import com.spring_api.back_end.data.dto.request.LoginRequest;
import com.spring_api.back_end.data.dto.request.RegisterRequest;
import com.spring_api.back_end.data.dto.response.AccountResponse;
import com.spring_api.back_end.data.entity.Account;
import com.spring_api.back_end.util.AESUtil;
import com.spring_api.back_end.util.Util;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountFacade {

    private final AccountService service;

    public AccountResponse register(RegisterRequest request) throws Exception {
        validatePasswordsMatch(request.getPassword(), request.getConfirmPassword());
        validateEmailUnique(request.getEmail());
        validatePhoneUnique(request.getPhone());

        SecretKey secretKey = AESUtil.generateKey();

        Account account = buildAccountFromRequest(request, secretKey);
        Account saved = service.save(account);
        return toResponse(saved);
    }

    public AccountResponse login(LoginRequest request) throws Exception {
        Account account = findAccountByEmailOrPhone(request.getEmailOrPhone());

        if(!account.isVerifyEmail()){
            throw new AccountIsNotVerifiedException();
        }

        validatePassword(AESUtil.decrypt(account.getPassword(), account.getSecretKey()), request.getPassword());
        return toResponse(account);
    }

    public AccountResponse deleteAccount(LoginRequest request) throws Exception {
        Account account = findAccountByEmailOrPhoneIsDeleted(request.getEmailOrPhone());
        validatePassword(AESUtil.decrypt(account.getPassword(), account.getSecretKey()), request.getPassword());
        validateIsDelete(account);
        account.setActive(false);
        account.setDeleted(true);
        Account saved = service.save(account);
        return toResponse(saved);
    }

    private void validatePasswordsMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new PasswordsMatchException();
        }
    }

    private void validateEmailUnique(String email) {
        if (!ObjectUtils.isEmpty(service.findByEmailAndIsDeleted(email, false))) {
            throw new EmailUniqueException();
        }
    }

    private void validatePhoneUnique(String phone) {
        if (!ObjectUtils.isEmpty(service.findByPhoneAndIsDeleted(phone, false))) {
            throw new PhoneUniqueException();
        }
    }

    private void validatePassword(String actual, String provided) {
        if (!actual.equals(provided)) {
            throw new LoginException();
        }
    }

    private void validateIsDelete(Account account) {
        if (account.isDeleted()) {
            throw new DeleteAccountException();
        }
    }

    private Account findAccountByEmailOrPhoneIsDeleted(String emailOrPhone) {
        Optional<Account> optional = emailOrPhone.contains("@")
                ? service.findByEmailOrderByIdDesc(emailOrPhone)
                : service.findByPhoneOrderByIdDesc(emailOrPhone);

        return optional.orElseThrow(LoginException::new);
    }

    private Account findAccountByEmailOrPhone(String emailOrPhone) {
        Optional<Account> optional = emailOrPhone.contains("@")
                ? service.findByEmailAndIsDeleted(emailOrPhone, false)
                : service.findByPhoneAndIsDeleted(emailOrPhone, false);

        return optional.orElseThrow(LoginException::new);
    }

    private Account buildAccountFromRequest(RegisterRequest request, SecretKey secretKey) throws Exception {
        return Account.builder()
                .id(Util.generateCustomId())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .dob(request.getDob())
                .gender(request.getGender())
                .password(AESUtil.encrypt(request.getPassword(), secretKey))
                .isDeleted(false)
                .isActive(true)
                .secretKey(secretKey)
                .build();
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
