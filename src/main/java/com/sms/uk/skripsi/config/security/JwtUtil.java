package com.sms.uk.skripsi.config.security;

import com.sms.uk.skripsi.core.properties.ConfigAppProperties;
import com.sms.uk.skripsi.module.user.entities.MasterUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

    private final ConfigAppProperties configAppProperties;

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generate(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("unklab.ac.id")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + configAppProperties.expToken()))
                .signWith(key)
                .compact();
    }

    public boolean validate(String token) {
        return getUsername(token) != null && isExpired(token);
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    public Claims getClaims(String token) {
        boolean checkNullOrBlank = Optional.ofNullable(token).orElse("").isBlank();
        if (checkNullOrBlank) {
            throw new JwtException("Token is null");
        }
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .requireIssuer("unklab.ac.id")
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateTokenLogout(MasterUser user) {
        log.info("[GENERATING USER TOKEN]");
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer("unklab.ac.id")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(key)
                .compact();
    }
}
