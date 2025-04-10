package io.github.wkktoria.game_reviews.security;

import java.util.Optional;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

public class SecurityConstants {

    public static final long JWT_EXPIRATION = 60 * 1000 * 60 * 24;
    public static final String JWT_SECRET = Optional.ofNullable(System.getenv("JWT_SECRET"))
            .orElseThrow(() -> new IllegalStateException("JWT_SECRET environment variable is missing"));
    public static final SecretKey JWT_SECRET_KEY = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
}
