package io.github.wkktoria.game_reviews.controllers;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.wkktoria.game_reviews.models.Game;

@RestController
@RequestMapping("/api/")
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
}
