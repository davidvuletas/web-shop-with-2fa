package com.authenticator.config.security;

import com.authenticator.exception.QRCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Component
public class DecryptionAlgorithm {

    private static String secretKeyFromProperties;
    private static String secretInitialFromProperties;

    public static String decryptData(String data) {
        try {
            data = decodeEscapedChars(data);
            IvParameterSpec iv = new IvParameterSpec(secretInitialFromProperties.getBytes(UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(secretKeyFromProperties.getBytes(UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(original);
        } catch (Exception e) {
            log.error("Error while encrypting: " + e.toString());
            throw new QRCodeException("QR code are not scanned successfully!");
        }
    }

    private static String decodeEscapedChars(String data) {
        return URLDecoder.decode(data.replaceAll("\\+", "%2b"), UTF_8);
    }

    @Value("${encrypt.key}")
    public void setSecretKeyFromProperties(String secretFromProp) {
        DecryptionAlgorithm.secretKeyFromProperties = secretFromProp;
    }

    @Value("${encrypt.initial}")
    public void setSecretInitialFromProperties(String secretInitialFromProp) {
        DecryptionAlgorithm.secretInitialFromProperties = secretInitialFromProp;
    }
}
