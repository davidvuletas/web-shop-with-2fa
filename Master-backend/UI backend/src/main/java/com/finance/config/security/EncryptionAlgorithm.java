package com.finance.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@Component
public class EncryptionAlgorithm {

    private static SecretKeySpec secretKey;

    @Value("${encrypt.key}")
    private String secretFromProp;

    private static String secretKeyFromProperties;

    public static String encryptData(String data) {
        try {
            setKey();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("Error while encrypting: " + e.toString());
        }
        return null;
    }


    public static void setKey() {
        MessageDigest sha = null;
        try {
            byte[] key = secretKeyFromProperties.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }
    }

    @Value("${encrypt.key}")
    public void setSecretKeyFromProperties(String secretFromProp) {
        EncryptionAlgorithm.secretKeyFromProperties = secretFromProp;
    }
}
