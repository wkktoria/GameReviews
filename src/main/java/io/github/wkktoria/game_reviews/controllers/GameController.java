package io.github.wkktoria.game_reviews.controllers;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
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

import io.github.wkktoria.game_reviews.models.Game;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/")
@Slf4j
public class GameController {

    @GetMapping("game")
    public ResponseEntity<List<Game>> getGames() {
        List<Game> games = new ArrayList<>();
        games.add(new Game(1,
                "Terraria", "Re-Logic", "505 Games",
                LocalDate.of(2011, Month.MAY, 16)));
        games.add(new Game(2,
                "Stardew Valley", "ConcernedApe", "ConcernedApe",
                LocalDate.of(2016, Month.FEBRUARY, 26)));
        games.add(new Game(3,
                "Minecraft", "Mojang Studios", "Mojang Studios",
                LocalDate.of(2011, Month.NOVEMBER, 11)));

        return ResponseEntity.ok(games);
    }

    @GetMapping("game/{id}")
    public Game gameDetail(@PathVariable int id) {
        return new Game(id, "Name", "Developer", "Publisher", new Date());
    }

    @PostMapping("game/create")
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        log.info(game.getName());
        return new ResponseEntity<>(game, HttpStatus.CREATED);
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
