package com.j2ee.J2EE.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.j2ee.J2EE.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByLoginName(String loginName);

    boolean existsByLoginName(String loginName);
}