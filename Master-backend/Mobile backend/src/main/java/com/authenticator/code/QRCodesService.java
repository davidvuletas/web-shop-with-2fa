package com.authenticator.code;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class QRCodesService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final QrCodesRepository qrCodesRepository;

    public boolean existsByQRCode(String qrCode) {
        return qrCodesRepository.existsByQrCode(qrCode);
    }
    public void createQRCode(String qrCode) {
        ReadQrCode readQrCode = ReadQrCode.builder().qrCode(qrCode)
                .readTime(OffsetDateTime.now().toLocalDateTime().format(formatter))
                .build();
        qrCodesRepository.save(readQrCode);
    }
}
