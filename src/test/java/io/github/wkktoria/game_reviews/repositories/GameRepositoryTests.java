package io.github.wkktoria.game_reviews.repositories;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import io.github.wkktoria.game_reviews.models.Game;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GameRepositoryTests {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void GameRepository_SaveAll_ReturnsSavedGame() {
        // Arrange
        Game game = Game.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();

        // Act
        Game savedGame = gameRepository.save(game);

        // Assert
        Assertions.assertThat(savedGame).isNotNull();
        Assertions.assertThat(savedGame.getId()).isGreaterThan(0);
    }
}
