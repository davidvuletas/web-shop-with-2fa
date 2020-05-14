package com.authenticator.account;

import com.authenticator.exception.AlreadyExistingException;
import com.authenticator.exception.InternalServerErrorException;
import com.authenticator.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final AccountRepository accountRepository;


    public Account createAccountByQrCodeData(String data) {
        try {
            JsonNode object = new ObjectMapper().readTree(data);
            Account account = mapJsonNodeToAccount(object);

            if (accountRepository.existsAccountByEmail(account.getEmail())) {
                log.error("Account for email {} is already registered",
                        account.getEmail());
                throw new AlreadyExistingException("This account is already registered");
            }
            return accountRepository.save(account);
        } catch (JsonProcessingException e) {
            log.error("Something went wrong during mapping, {}", e.getMessage());
            throw new InternalServerErrorException("Something went wrong during mapping data");
        }
    }

    public Account doValidation(String data, String mail) {
        try {
            JsonNode obj = new ObjectMapper().readTree(data);
            Account account = mapJsonNodeToAccount(obj);
            if (!account.getEmail().equals(mail)) {
                log.error("Reading QR code of another user");
                throw new NotFoundException("Not permitted to scan QR code for another user!");
            }
            if (!accountRepository.existsAccountByEmail(account.getEmail())) {
                log.error("Account not exists");
                throw new NotFoundException("This account is not exists!");
            } else {
                Account fromDb = accountRepository.findAccountByEmail(account.getEmail());
                LocalDateTime now = OffsetDateTime.now().toLocalDateTime();
                fromDb.setLastActivity(now.format(formatter));
                return accountRepository.save(fromDb);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Something went wrong during mapping, {}", e.getMessage());
            throw new InternalServerErrorException("Something went wrong during mapping data");
        }
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    private Account mapJsonNodeToAccount(JsonNode object) {
        //TODO Uncomment at the end
        /*String validUntilString = object.get("validTimeUntil").asText();
        LocalDateTime validUntilTime = LocalDateTime.parse(validUntilString, formatter);
        if(validUntilTime.isBefore(LocalDateTime.now())) {
            throw new QRCodeException("QR code is expired!");
        }*/
        return Account.builder()
                .email(object.get("email").asText())
                .lastActivity(object.get("generatedTime").asText())
                .role(Role.valueOf(object.get("role").asText()))
                .build();
    }
}
