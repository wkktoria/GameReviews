package io.github.wkktoria.game_reviews.services;

import io.github.wkktoria.game_reviews.dtos.GameDto;
import io.github.wkktoria.game_reviews.dtos.GameResponse;

public interface GameService {

    GameDto createGame(GameDto gameDto);

    GameResponse getAllGame(int pageNumber, int pageSize);

    GameDto getGameById(int id);

    GameDto updateGame(GameDto gameDto, int id);

    void deleteGame(int id);
}
