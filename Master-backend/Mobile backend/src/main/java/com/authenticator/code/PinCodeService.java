package com.authenticator.code;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PinCodeService {

    private final PinCodeRepository pinCodeRepository;

    public void storeAuthenticationCode(PinCode pinCode) {
        pinCodeRepository.save(pinCode);
    }

    public boolean existsByCodeAndMail(String code, String mail, String application) {
        return pinCodeRepository.existsByCodeAndEmailAndApplication(code, mail, application);
    }
}
