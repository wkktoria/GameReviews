package io.github.wkktoria.game_reviews.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.wkktoria.game_reviews.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByGameId(int gameId);
}
