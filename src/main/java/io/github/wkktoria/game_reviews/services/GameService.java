package io.github.wkktoria.game_reviews.services;

import io.github.wkktoria.game_reviews.dtos.GameDto;

public interface GameService {

    GameDto createGame(GameDto gameDto);
}
