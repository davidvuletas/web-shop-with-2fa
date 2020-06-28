package com.authenticator.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PinCodeRepository extends JpaRepository<PinCode, Long> {

    boolean existsByCodeAndEmailAndApplication(String code, String mail, String application);
}
