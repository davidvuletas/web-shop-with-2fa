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

    private static String secretKeyFromPropertiesWeb;
    private static String secretInitialFromPropertiesWeb;

    private static String secretKeyFromPropertiesFinance;
    private static String secretInitialFromPropertiesFinance;

    public static String decryptData(String data) {
        try {
            IvParameterSpec iv;
            SecretKeySpec skeySpec;
            if(data.contains("applicationName: finance")) {
                iv = new IvParameterSpec(secretInitialFromPropertiesFinance.getBytes(UTF_8));
                skeySpec = new SecretKeySpec(secretKeyFromPropertiesFinance.getBytes(UTF_8), "AES");
            } else {
                iv = new IvParameterSpec(secretInitialFromPropertiesWeb.getBytes(UTF_8));
                skeySpec = new SecretKeySpec(secretKeyFromPropertiesWeb.getBytes(UTF_8), "AES");
            }
            data = data.split(";")[0];
            data = decodeEscapedChars(data);
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

    @Value("${encrypt-web.key}")
    public void setSecretKeyFromPropertiesWeb(String secretFromProp) {
        DecryptionAlgorithm.secretKeyFromPropertiesWeb = secretFromProp;
    }

    @Value("${encrypt-web.initial}")
    public void setSecretInitialFromPropertiesWeb(String secretInitialFromProp) {
        DecryptionAlgorithm.secretInitialFromPropertiesWeb = secretInitialFromProp;
    }

    @Value("${encrypt-finance.key}")
    public void setSecretKeyFromPropertiesFinance(String secretFromProp) {
        DecryptionAlgorithm.secretKeyFromPropertiesFinance = secretFromProp;
    }

    @Value("${encrypt-finance.initial}")
    public void setSecretInitialFromPropertiesFinance(String secretInitialFromProp) {
        DecryptionAlgorithm.secretInitialFromPropertiesFinance = secretInitialFromProp;
    }
}
