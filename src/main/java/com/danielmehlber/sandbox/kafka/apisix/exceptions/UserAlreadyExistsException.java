package com.danielmehlber.sandbox.kafka.apisix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String username) {
        super(String.format("Cannot create user '%s' because it already exists", username));
    }

}
