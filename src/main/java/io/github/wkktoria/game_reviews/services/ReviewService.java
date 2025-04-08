package io.github.wkktoria.game_reviews.services;

import java.util.List;

import io.github.wkktoria.game_reviews.dtos.ReviewDto;

public interface ReviewService {

    ReviewDto createReview(int gameId, ReviewDto reviewDto);

    List<ReviewDto> getReviewsByGameId(int id);
}
