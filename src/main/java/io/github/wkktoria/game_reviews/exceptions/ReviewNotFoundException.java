package io.github.wkktoria.game_reviews.exceptions;

public class ReviewNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public ReviewNotFoundException() {
        super("Review not found");
    }

    public ReviewNotFoundException(final String message) {
        super(message);
    }
}
