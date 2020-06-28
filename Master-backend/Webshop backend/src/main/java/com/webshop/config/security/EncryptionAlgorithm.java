package com.webshop.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Component
public class EncryptionAlgorithm {

    private static String secretKeyFromProperties;
    private static String secretInitialFromProperties;

    public static String encryptData(String data) {
        try {
            IvParameterSpec iv = new IvParameterSpec(secretInitialFromProperties.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(secretKeyFromProperties.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("Error while encrypting: " + e.toString());
        }
        return null;
    }

    @Value("${encrypt.key}")
    public void setSecretKeyFromProperties(String secretFromProp) {
        EncryptionAlgorithm.secretKeyFromProperties = secretFromProp;
    }

    @Value("${encrypt.initial}")
    public void setSecretInitialFromProperties(String secretInitialFromProp) {
        EncryptionAlgorithm.secretInitialFromProperties = secretInitialFromProp;
    }
}
