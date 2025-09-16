package com.spring_api.back_end.business.service;

import com.spring_api.back_end.data.entity.Account;
import com.spring_api.back_end.data.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;

    public Account save(Account account){
        return repository.save(account);
    }

    public Optional<Account> findByEmailOrderByIdDesc(String email){
        return repository.findByEmailOrderByIdDesc(email);
    }

    public Optional<Account> findByPhoneOrderByIdDesc(String phone){
        return repository.findByPhoneOrderByIdDesc(phone);
    }

    public Optional<Account> findByEmailAndIsDeleted(String email, boolean isDeleted){
        return repository.findByEmailAndIsDeleted(email, isDeleted);
    }

    public Optional<Account> findByPhoneAndIsDeleted(String phone, boolean isDeleted){
        return repository.findByPhoneAndIsDeleted(phone, isDeleted);
    }
}
