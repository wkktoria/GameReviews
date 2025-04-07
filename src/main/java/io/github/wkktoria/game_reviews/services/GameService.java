package io.github.wkktoria.game_reviews.services;

import java.util.List;

import io.github.wkktoria.game_reviews.dtos.GameDto;

public interface GameService {

    GameDto createGame(GameDto gameDto);

    List<GameDto> getAllGame();

    GameDto getGameById(int id);
}
