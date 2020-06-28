package com.webshop.qrcode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class QRCode {
    private String email;
    private String generatedTime;
    private String validTimeUntil;
    @JsonIgnore
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");


    public void generateNewTime() {
        LocalDateTime tempStart = OffsetDateTime.now().toLocalDateTime();
        generatedTime = tempStart.format(formatter);
        LocalDateTime tempEnd = OffsetDateTime.now().plusMinutes(1).toLocalDateTime();
        validTimeUntil = tempEnd.format(formatter);
    }

    @SneakyThrows
    public String asString() {
        return new ObjectMapper().writeValueAsString(this);
    }
}
