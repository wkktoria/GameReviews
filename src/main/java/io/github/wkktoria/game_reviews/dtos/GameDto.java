package io.github.wkktoria.game_reviews.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class GameDto {

    private int id;
    private String name;
    private String developer;
    private String publisher;
    private LocalDate releaseDate;
}
