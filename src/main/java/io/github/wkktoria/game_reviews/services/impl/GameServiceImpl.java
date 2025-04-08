package io.github.wkktoria.game_reviews.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.wkktoria.game_reviews.dtos.GameDto;
import io.github.wkktoria.game_reviews.dtos.GameResponse;
import io.github.wkktoria.game_reviews.exceptions.GameNotFoundException;
import io.github.wkktoria.game_reviews.models.Game;
import io.github.wkktoria.game_reviews.repositories.GameRepository;
import io.github.wkktoria.game_reviews.services.GameService;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public GameDto createGame(GameDto gameDto) {
        Game game = mapToEntity(gameDto);
        Game newGame = gameRepository.save(game);

        GameDto gameResponse = mapToDto(newGame);
        return gameResponse;
    }

    @Override
    public GameResponse getAllGame(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Game> games = gameRepository.findAll(pageable);
        List<Game> listOfGame = games.getContent();
        List<GameDto> content = listOfGame.stream()
                .map(game -> mapToDto(game)).collect(Collectors.toList());

        GameResponse gameResponse = GameResponse.builder()
                .content(content).pageNumber(games.getNumber() + 1).pageSize(games.getSize())
                .totalElements(games.getTotalElements()).totalPages(games.getTotalPages()).last(games.isLast())
                .build();
        return gameResponse;
    }

    @Override
    public GameDto getGameById(int id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game could not be found"));
        return mapToDto(game);
    }

    @Override
    public GameDto updateGame(GameDto gameDto, int id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game could not be updated"));
        game.setName(gameDto.getName());
        game.setDeveloper(gameDto.getDeveloper());
        game.setPublisher(gameDto.getPublisher());
        game.setReleaseDate(gameDto.getReleaseDate());

        Game updatedGame = gameRepository.save(game);
        return mapToDto(updatedGame);
    }

    @Override
    public void deleteGame(int id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game could not be deleted"));
        gameRepository.delete(game);
    }

    private GameDto mapToDto(Game game) {
        GameDto gameDto = GameDto.builder()
                .id(game.getId())
                .name(game.getName())
                .developer(game.getDeveloper())
                .publisher(game.getPublisher())
                .releaseDate(game.getReleaseDate())
                .build();
        return gameDto;
    }

    private Game mapToEntity(GameDto gameDto) {
        Game game = Game.builder()
                .id(gameDto.getId())
                .name(gameDto.getName())
                .developer(gameDto.getDeveloper())
                .publisher(gameDto.getPublisher())
                .releaseDate(gameDto.getReleaseDate())
                .build();
        return game;
    }
}
