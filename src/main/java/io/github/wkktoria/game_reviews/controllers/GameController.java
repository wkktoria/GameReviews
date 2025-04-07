package io.github.wkktoria.game_reviews.controllers;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.wkktoria.game_reviews.dtos.GameDto;
import io.github.wkktoria.game_reviews.models.Game;
import io.github.wkktoria.game_reviews.services.GameService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/")
@Slf4j
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("game")
    public ResponseEntity<List<GameDto>> getGames() {
        return new ResponseEntity<>(gameService.getAllGame(), HttpStatus.OK);
    }

    @GetMapping("game/{id}")
    public Game gameDetail(@PathVariable int id) {
        return new Game(id, "Name", "Developer", "Publisher", LocalDate.now());
    }

    @PostMapping("game/create")
    public ResponseEntity<GameDto> createGame(@RequestBody GameDto gameDto) {
        return new ResponseEntity<>(gameService.createGame(gameDto), HttpStatus.CREATED);
    }

    @PutMapping("game/{id}/update")
    public ResponseEntity<Game> updateGame(@RequestBody Game game, @PathVariable("id") int gameId) {
        log.info(game.getName());
        return ResponseEntity.ok(game);
    }

    @DeleteMapping("game/{id}/delete")
    public ResponseEntity<String> deleteGame(@PathVariable("id") int gameId) {
        log.info(String.valueOf(gameId));
        return ResponseEntity.ok("Game deleted successfully");
    }
}
