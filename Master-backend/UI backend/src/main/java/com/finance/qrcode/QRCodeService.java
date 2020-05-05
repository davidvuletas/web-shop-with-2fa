package com.finance.qrcode;

import com.finance.config.security.EncryptionAlgorithm;
import com.finance.user.User;
import com.finance.user.UserRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Slf4j
@AllArgsConstructor
@Service
public class QRCodeService {

    private final QRCodeWriter qrCodeWriter = new QRCodeWriter();

    private final UserRepository userRepository;

    @SneakyThrows
    public String generateQRCode(String email) {
        User user = userRepository.findUserByEmail(email);
        QRCode valueOfQRCode = QRCode.builder().email(email).role(user.getRole()).build();
        valueOfQRCode.generateNewTime();
        String data = EncryptionAlgorithm.encryptData(valueOfQRCode.asString());
        BitMatrix matrix = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            matrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 350, 350);
            MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);
        } catch (WriterException e) {
            log.error("Error while generating QR code {}", e.getMessage());
        }
        String base64bytes = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        return "data:image/png;base64," + base64bytes;
    }
}
