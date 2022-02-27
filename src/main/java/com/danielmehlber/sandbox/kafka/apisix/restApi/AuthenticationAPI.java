package com.danielmehlber.sandbox.kafka.apisix.restApi;


import com.danielmehlber.sandbox.kafka.apisix.entities.User;
import com.danielmehlber.sandbox.kafka.apisix.exceptions.ApiServiceFailedException;
import com.danielmehlber.sandbox.kafka.apisix.exceptions.NoSuchUserException;
import com.danielmehlber.sandbox.kafka.apisix.exceptions.WrongPasswordException;
import com.danielmehlber.sandbox.kafka.apisix.logic.UserLogic;
import com.danielmehlber.sandbox.kafka.apisix.services.ApisixService;
import com.danielmehlber.sandbox.kafka.apisix.util.JwtTokenGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

/**
 * This class is used to authenticate users in order to grant them access to user services.
 *
 * Access control is managed by the API Gateway using JWT. Those tokens will be created in this service and
 * passed to the gateway after successful authentication using its API.
 */
@RestController
public class AuthenticationAPI {

    @Autowired
    private UserLogic userLogic;

    @Autowired
    private ApisixService apisix;

    /**
     * Used to authenticate user with username and password using the Gateway API.
     *
     * The Gateway is responsible for access control and needs JWT session tokens, which are generated in this process
     * and passed to the gateway. Furthermore, this Post mapping (=route) is the only one accessible without
     * authentication (and therefore the first valid access point of a client).
     * @param username username of user
     * @param password his password in plain text format
     */
    @PostMapping("/auth/{username}")
    public String auth(@PathVariable final String username, @RequestBody final String password) throws NoSuchUserException, NoSuchAlgorithmException, WrongPasswordException, ApiServiceFailedException {
        // first authenticate user (using user database)
        User user = userLogic.authenticateUser(username, password);

        // then generate JWT token
        String token = JwtTokenGenerator.generate();

        // pass it to Gateway. After that the user can access other routes/mappings (and services)
        apisix.addConsumerAndToken(user, token);

        return token;
    }

}
