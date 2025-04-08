package io.github.wkktoria.game_reviews.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.wkktoria.game_reviews.dtos.ReviewDto;
import io.github.wkktoria.game_reviews.exceptions.GameNotFoundException;
import io.github.wkktoria.game_reviews.exceptions.ReviewNotFoundException;
import io.github.wkktoria.game_reviews.models.Game;
import io.github.wkktoria.game_reviews.models.Review;
import io.github.wkktoria.game_reviews.repositories.GameRepository;
import io.github.wkktoria.game_reviews.repositories.ReviewRepository;
import io.github.wkktoria.game_reviews.services.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private GameRepository gameRepository;

    private static final String GAME_NOT_FOUND_MESSAGE = "Game with associated review not found";
    private static final String REVIEW_NOT_FOUND_MESSAGE = "Review with associated game not found";
    private static final String REVIEW_ID_NOT_MATCH_MESSAGE = "This review does not belong to a game";

    public ReviewServiceImpl(ReviewRepository reviewRepository, GameRepository gameRepository) {
        this.reviewRepository = reviewRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public ReviewDto createReview(int gameId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND_MESSAGE));

        review.setGame(game);

        Review newReview = reviewRepository.save(review);
        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByGameId(int id) {
        List<Review> reviews = reviewRepository.findByGameId(id);
        return reviews.stream()
                .map(review -> mapToDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND_MESSAGE));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND_MESSAGE));

        if (review.getGame().getId() != game.getId()) {
            throw new ReviewNotFoundException(REVIEW_ID_NOT_MATCH_MESSAGE);
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int gameId, int reviewId, ReviewDto reviewDto) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND_MESSAGE));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND_MESSAGE));

        if (review.getGame().getId() != game.getId()) {
            throw new ReviewNotFoundException(REVIEW_ID_NOT_MATCH_MESSAGE);
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updatedReview = reviewRepository.save(review);
        return mapToDto(updatedReview);
    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = ReviewDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .stars(review.getStars())
                .build();
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = Review.builder()
                .id(reviewDto.getId())
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .stars(reviewDto.getStars())
                .build();
        return review;
    }
}
