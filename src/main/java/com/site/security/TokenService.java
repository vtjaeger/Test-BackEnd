package com.site.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.site.models.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(UserModel userModel){
        Algorithm algorimo = Algorithm.HMAC256(secret);
        try {
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(userModel.getName())
                    .withExpiresAt(expiracaoToken())
                    .sign(algorimo);
        } catch (JWTCreationException ex){
            throw new RuntimeException("Erro na geracao do token" + ex.getMessage());
        }

    }

    private Instant expiracaoToken(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validarToken(String token){
        try {
            Algorithm algorimo =  Algorithm.HMAC256(secret);
            return JWT.require(algorimo)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex){
            return "";
        }
    }
}
