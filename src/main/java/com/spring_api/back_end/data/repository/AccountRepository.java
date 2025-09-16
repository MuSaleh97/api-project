package com.spring_api.back_end.data.repository;

import com.spring_api.back_end.data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmailOrderByIdDesc(String email);
    Optional<Account> findByPhoneOrderByIdDesc(String phone);

    Optional<Account> findByEmailAndIsDeleted(String email, boolean isDeleted);
    Optional<Account> findByPhoneAndIsDeleted(String phone, boolean isDeleted);
}
