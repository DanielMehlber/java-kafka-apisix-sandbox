package com.danielmehlber.sandbox.kafka.apisix.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtTokenGenerator {

    public static String generate() {
        // create token
        Algorithm algorithm = Algorithm.HMAC256("secret"); // TODO: Change JWT secret

        return JWT.create()
                .sign(algorithm);

    }

}
