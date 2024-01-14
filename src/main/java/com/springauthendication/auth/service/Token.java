package com.springauthendication.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Getter
@AllArgsConstructor
public class Token {
    private String token;

    public static Token of (int id , Long validityInMinutes, String secretKey ){
        var issueDate = Instant.now();
        return new Token(
                Jwts.builder()
                        .claim("id", id)
                        .setIssuedAt(Date.from(issueDate))
                        .setExpiration(Date.from(issueDate.plus(validityInMinutes, ChronoUnit.MINUTES)))
                        .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString( secretKey.getBytes(StandardCharsets.UTF_8)))
                        .compact()  );
    }
}
