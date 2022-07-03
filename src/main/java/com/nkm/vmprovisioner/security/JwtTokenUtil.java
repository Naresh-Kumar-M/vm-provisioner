package com.nkm.vmprovisioner.security;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nkm.vmprovisioner.domain.Account;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtTokenUtil {

    private final String secret = "secret";

    public JwtToken generateToken(Account account) throws IllegalArgumentException, JWTCreationException {
        return new JwtToken(JWT.create()
                .withSubject(account.getEmailId())
                .withClaim("role", account.getRole().name())
                .sign(Algorithm.HMAC256(secret)));
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }
}