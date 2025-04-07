package io.github.wkktoria.game_reviews.exceptions;

public class GameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public GameNotFoundException(String message) {
        super(message);
    }
}
