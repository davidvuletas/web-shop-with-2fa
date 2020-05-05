package com.authenticator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QRCodeException extends RuntimeException{

    public QRCodeException(String msg) {
        super(msg);
    }
}
