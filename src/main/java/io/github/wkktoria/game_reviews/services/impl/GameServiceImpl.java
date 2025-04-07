package io.github.wkktoria.game_reviews.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.wkktoria.game_reviews.dtos.GameDto;
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
    public List<GameDto> getAllGame() {
        List<Game> gameList = gameRepository.findAll();
        return gameList.stream().map(game -> mapToDto(game)).collect(Collectors.toList());
    }

    private GameDto mapToDto(Game game) {
        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setName(game.getName());
        gameDto.setDeveloper(game.getDeveloper());
        gameDto.setPublisher(game.getPublisher());
        gameDto.setReleaseDate(game.getReleaseDate());
        return gameDto;
    }

    private Game mapToEntity(GameDto gameDto) {
        Game game = new Game();
        game.setId(gameDto.getId());
        game.setName(gameDto.getName());
        game.setDeveloper(gameDto.getDeveloper());
        game.setPublisher(gameDto.getPublisher());
        game.setReleaseDate(gameDto.getReleaseDate());
        return game;
    }
}
