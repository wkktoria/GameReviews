package io.github.wkktoria.game_reviews.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.wkktoria.game_reviews.dtos.GameDto;
import io.github.wkktoria.game_reviews.dtos.GameResponse;
import io.github.wkktoria.game_reviews.models.Game;
import io.github.wkktoria.game_reviews.repositories.GameRepository;
import io.github.wkktoria.game_reviews.services.impl.GameServiceImpl;

@ExtendWith(MockitoExtension.class)
public class GameServiceTests {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    public void GameService_CreateGame_ReturnsGameDto() {
        // Arrange
        Game game = Game.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();
        GameDto gameDto = GameDto.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();

        when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game);

        // Act
        GameDto savedGame = gameService.createGame(gameDto);

        // Assert
        Assertions.assertThat(savedGame).isNotNull();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void GameService_GetAllGame_ReturnsGameResponse() {
        // Arrange
        Page<Game> games = Mockito.mock(Page.class);
        when(gameRepository.findAll(Mockito.any(Pageable.class))).thenReturn(games);

        // Act
        GameResponse savedGame = gameService.getAllGame(1, 10);

        // Assert
        Assertions.assertThat(savedGame).isNotNull();
    }

    @Test
    public void GameService_GetGameById_ReturnsGameDto() {
        // Arrange
        Game game = Game.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();

        when(gameRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(game));

        // Act
        GameDto savedGame = gameService.getGameById(1);

        // Assert
        Assertions.assertThat(savedGame).isNotNull();
    }

    @Test
    public void GameService_UpdateGame_ReturnsGameDto() {
        // Arrange
        Game game = Game.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();
        GameDto gameDto = GameDto.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();

        when(gameRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(game));
        when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game);

        // Act
        GameDto savedGame = gameService.updateGame(gameDto, 1);

        // Assert
        Assertions.assertThat(savedGame).isNotNull();
    }

    @Test
    public void GameService_DeleteGame_ReturnsVoid() {
        // Arrange
        Game game = Game.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();

        when(gameRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(game));

        // Act
        // Assert
        assertAll(() -> gameService.deleteGame(1));
    }
}
