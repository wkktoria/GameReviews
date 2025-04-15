package io.github.wkktoria.game_reviews.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.wkktoria.game_reviews.dtos.GameDto;
import io.github.wkktoria.game_reviews.dtos.ReviewDto;
import io.github.wkktoria.game_reviews.models.Game;
import io.github.wkktoria.game_reviews.models.Review;
import io.github.wkktoria.game_reviews.repositories.GameRepository;
import io.github.wkktoria.game_reviews.repositories.ReviewRepository;
import io.github.wkktoria.game_reviews.services.impl.ReviewServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Game game;
    private GameDto gameDto;
    private Review review;
    private ReviewDto reviewDto;

    @BeforeEach
    public void init() {
        game = Game.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();
        gameDto = GameDto.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();

        review = Review.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();
        reviewDto = ReviewDto.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();
    }

    @Test
    public void ReviewService_CreateReview_ReturnsReviewDto() {
        // Arrange
        when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        // Act
        ReviewDto savedReview = reviewService.createReview(game.getId(), reviewDto);

        // Assert
        Assertions.assertThat(savedReview).isNotNull();
    }

    @Test
    public void ReviewService_GetReviewsByGameId_ReturnsListOfReviewDto() {
        // Arrange
        when(reviewRepository.findByGameId(Mockito.anyInt())).thenReturn(Arrays.asList(review));

        // Act
        List<ReviewDto> returnReview = reviewService.getReviewsByGameId(1);

        // Assert
        Assertions.assertThat(returnReview).isNotNull();
    }

    @Test
    public void ReviewService_GetReviewById_ReturnsReviewDto() {
        // Arrange
        review.setGame(game);
        when(reviewRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(review));
        when(gameRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(game));

        // Act
        ReviewDto returnReview = reviewService.getReviewById(1, 1);

        // Assert
        Assertions.assertThat(returnReview).isNotNull();
    }

    @Test
    public void ReviewService_UpdateReview_ReturnsReviewDto() {
        // Arrange
        game.setReviews(Arrays.asList(review));
        review.setGame(game);

        when(reviewRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(review));
        when(gameRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(game));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        // Act
        ReviewDto returnReview = reviewService.updateReview(1, 1, reviewDto);

        // Assert
        Assertions.assertThat(returnReview).isNotNull();
    }

    @Test
    public void ReviewService_DeleteReview_ReturnsVoid() {
        // Arrange
        game.setReviews(Arrays.asList(review));
        review.setGame(game);

        when(reviewRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(review));
        when(gameRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(game));

        // Act
        // Assert
        assertAll(() -> reviewService.deleteReview(1, 1));
    }
}
