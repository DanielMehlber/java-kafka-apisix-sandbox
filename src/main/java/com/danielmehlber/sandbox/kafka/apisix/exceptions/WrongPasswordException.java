package com.danielmehlber.sandbox.kafka.apisix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class WrongPasswordException extends Exception {

    public WrongPasswordException(final String username) {
        super(String.format("Authentication failed: Wrong password for user '%s'", username));
    }

}
