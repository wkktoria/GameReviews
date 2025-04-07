package io.github.wkktoria.game_reviews.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.wkktoria.game_reviews.dtos.GameDto;
import io.github.wkktoria.game_reviews.dtos.GameResponse;
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
    public ResponseEntity<GameResponse> getGames(@RequestParam(defaultValue = "1", required = false) int pageNumber,
            @RequestParam(defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(gameService.getAllGame(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("game/{id}")
    public ResponseEntity<GameDto> gameDetail(@PathVariable int id) {
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @PostMapping("game/create")
    public ResponseEntity<GameDto> createGame(@RequestBody GameDto gameDto) {
        return new ResponseEntity<>(gameService.createGame(gameDto), HttpStatus.CREATED);
    }

    @PutMapping("game/{id}/update")
    public ResponseEntity<GameDto> updateGame(@RequestBody GameDto ganDto, @PathVariable("id") int gameId) {
        GameDto response = gameService.updateGame(ganDto, gameId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("game/{id}/delete")
    public ResponseEntity<String> deleteGame(@PathVariable("id") int gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.ok("Game deleted");
    }
}
