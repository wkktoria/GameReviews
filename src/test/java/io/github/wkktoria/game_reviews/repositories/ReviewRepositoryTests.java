package io.github.wkktoria.game_reviews.repositories;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import io.github.wkktoria.game_reviews.models.Review;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void ReviewRepository_Save_ReturnsSavedReview() {
        // Arrange
        Review review = Review.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();

        // Act
        Review savedReview = reviewRepository.save(review);

        // Assert
        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_FindAll_ReturnsMoreThanOneReview() {
        // Arrange
        Review review1 = Review.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();
        Review review2 = Review.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        // Act
        List<Review> reviewList = reviewRepository.findAll();

        // Assert
        Assertions.assertThat(reviewList).isNotNull();
        Assertions.assertThat(reviewList.size()).isGreaterThan(1);
    }

    @Test
    public void ReviewRepository_FindById_ReturnsSavedReview() {
        // Arrange
        Review review = Review.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();

        reviewRepository.save(review);

        // Act
        Review returnReview = reviewRepository.findById(review.getId()).get();

        // Assert
        Assertions.assertThat(returnReview).isNotNull();
    }

    @Test
    public void ReviewRepository_UpdateReview_ReturnsReview() {
        // Arrange
        Review review = Review.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();

        reviewRepository.save(review);

        Review savedReview = reviewRepository.findById(review.getId()).get();
        savedReview.setTitle("Updated Title");
        savedReview.setContent("Updated Content");

        // Act
        Review updatedReview = reviewRepository.save(savedReview);

        // Assert
        Assertions.assertThat(updatedReview.getTitle()).isNotNull();
        Assertions.assertThat(updatedReview.getContent()).isNotNull();
    }

    @Test
    public void ReviewRepository_DeleteById_ReturnReviewIsEmpty() {
        // Arrange
        Review review = Review.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();

        reviewRepository.save(review);

        // Act
        reviewRepository.deleteById(review.getId());
        Optional<Review> returnReview = reviewRepository.findById(review.getId());

        // Assert
        Assertions.assertThat(returnReview).isEmpty();
    }
}
