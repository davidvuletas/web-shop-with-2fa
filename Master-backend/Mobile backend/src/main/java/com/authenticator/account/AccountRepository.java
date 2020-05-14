package com.authenticator.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsAccountByEmail(String email);
    Account findAccountByEmail(String email);
}
