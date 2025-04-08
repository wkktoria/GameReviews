package io.github.wkktoria.game_reviews.security;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        String token = Jwts.builder()
                .subject(username).issuedAt(new Date()).expiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes()))
                .compact();
        return token;
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes())).build()
                .parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes())).build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception exception) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
