package io.github.wkktoria.game_reviews.models;

import java.util.Date;
import lombok.Data;

@Data
public class Game {

    private int id;
    private String name;
    private String developer;
    private String publisher;
    private Date releaseDate;
}
