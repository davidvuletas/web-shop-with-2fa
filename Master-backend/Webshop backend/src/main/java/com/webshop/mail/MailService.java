package com.webshop.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${mobile.backend.url}")
    private String mobileBackendUrl;

    private final JavaMailSender mailSender;
    private final RestTemplate restTemplate;

    public MailService(JavaMailSender mailSender, RestTemplate restTemplate) {
        this.mailSender = mailSender;
        this.restTemplate = restTemplate;
    }

    @Async
    public void sendMail(String to, String code) {

        AuthenticationCode authenticationCode = AuthenticationCode.builder().code(code).applicationName("webshop").email(to).build();
        restTemplate.postForEntity(mobileBackendUrl + "/code" , authenticationCode, AuthenticationCode.class);

        SimpleMailMessage message = new SimpleMailMessage();
        String messageText = "Thanks for choosing our system for two factor authentication!\n" +
                "Your 4-digit code is: %s\n\n\n" +
                "Authenticator application";
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject("Registration 4-digit code");
        message.setText(String.format(messageText, code));
        mailSender.send(message);
    }
}
