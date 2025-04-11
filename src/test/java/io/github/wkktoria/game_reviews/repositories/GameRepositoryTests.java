package io.github.wkktoria.game_reviews.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Test
    public void GameRepository_FindAll_ReturnsMoreThanOneGame() {
        // Arrange
        Game game1 = Game.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();
        Game game2 = Game.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();

        gameRepository.save(game1);
        gameRepository.save(game2);

        // Act
        List<Game> gameList = gameRepository.findAll();

        // Assert
        Assertions.assertThat(gameList).isNotNull();
        Assertions.assertThat(gameList.size()).isEqualTo(2);
    }

    @Test
    public void GameRepository_FindById_ReturnsGame() {
        // Arrange
        Game game = Game.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();
        gameRepository.save(game);

        // Act
        Game returnGame = gameRepository.findById(game.getId()).get();

        // Assert
        Assertions.assertThat(returnGame).isNotNull();
    }

    @Test
    public void GameRepository_FindByName_ReturnsGameNotNull() {
        // Arrange
        Game game = Game.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();
        gameRepository.save(game);

        // Act
        Game returnGame = gameRepository.findByName(game.getName()).get();

        // Assert
        Assertions.assertThat(returnGame).isNotNull();
    }

    @Test
    public void GameRepository_UpdateGame_ReturnsGameNotNull() {
        // Arrange
        Game game = Game.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();
        gameRepository.save(game);

        Game savedGame = gameRepository.findById(game.getId()).get();
        savedGame.setName("Updated Name");
        savedGame.setDeveloper("Updated Developer");

        // Act
        Game updatedGame = gameRepository.save(savedGame);

        // Assert
        Assertions.assertThat(updatedGame).isNotNull();
        Assertions.assertThat(updatedGame.getName()).isNotNull();
        Assertions.assertThat(updatedGame.getDeveloper()).isNotNull();
    }

    @Test
    public void GameRepository_DeleteById_ReturnsGamIsEmpty() {
        // Arrange
        Game game = Game.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();
        gameRepository.save(game);

        // Act
        gameRepository.deleteById(game.getId());
        Optional<Game> returnGame = gameRepository.findById(game.getId());

        // Assert
        Assertions.assertThat(returnGame).isEmpty();
    }
}
