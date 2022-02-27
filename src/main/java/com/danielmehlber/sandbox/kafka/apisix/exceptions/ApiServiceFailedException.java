package com.danielmehlber.sandbox.kafka.apisix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * This exception occurs when an API call was not successful.
 *
 * This is a severe error and developer fault. Therefor it MUST always be logged and reported to the developers.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiServiceFailedException extends Exception {

    public ApiServiceFailedException(String apiName, String message, Throwable cause) {
        super(String.format("An API call to %s did not succeed: %s", apiName, message), cause);
    }

}
