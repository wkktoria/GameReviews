package io.github.wkktoria.game_reviews.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.wkktoria.game_reviews.models.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findByName(final String name);
}
