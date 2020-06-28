package com.authenticator.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsAccountByEmailAndApplication(String email, String app);

    Account findAccountByEmailAndApplication(String email, String app);
}
