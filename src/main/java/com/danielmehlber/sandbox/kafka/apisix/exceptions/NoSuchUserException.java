package com.danielmehlber.sandbox.kafka.apisix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchUserException extends Exception {

    public NoSuchUserException(final String username) {
        super(String.format("There is not user with username '%s'", username));
    }

}
