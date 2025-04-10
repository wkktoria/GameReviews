package io.github.wkktoria.game_reviews.dtos;

import lombok.Data;

@Data
public class AuthResponse {

    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponse(final String accessToken) {
        this.accessToken = accessToken;
    }
}
