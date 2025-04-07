package io.github.wkktoria.game_reviews.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.github.wkktoria.game_reviews.dtos.GameDto;
import io.github.wkktoria.game_reviews.models.Game;
import io.github.wkktoria.game_reviews.repositories.GameRepository;
import io.github.wkktoria.game_reviews.services.GameService;

@Service
public class GameServiceImpl implements GameService {

    private final ModelMapper modelMapper = new ModelMapper();
    private GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public GameDto createGame(GameDto gameDto) {
        Game game = modelMapper.map(gameDto, Game.class);
        Game newGame = gameRepository.save(game);

        GameDto gameResponse = modelMapper.map(newGame, GameDto.class);
        return gameResponse;
    }
}
