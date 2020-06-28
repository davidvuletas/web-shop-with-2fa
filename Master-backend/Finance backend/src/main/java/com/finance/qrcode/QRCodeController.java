package com.finance.qrcode;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(value = "https://localhost:8080")
@RequestMapping("/qr-code")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    @PostMapping("/{email}")
    public String generateQRCode(@PathVariable String email) {
        return qrCodeService.generateQRCode(email);
    }
}
