package com.authenticator.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodesRepository extends JpaRepository<ReadQrCode, Long> {


    boolean existsByQrCode(String qrCode);
}
