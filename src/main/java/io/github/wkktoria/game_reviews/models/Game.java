package io.github.wkktoria.game_reviews.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String developer;
    private String publisher;
    private Date releaseDate;

    public Game(int id, String name, String developer, String publisher, LocalDate releaseDate) {
        this.id = id;
        this.name = name;
        this.developer = developer;
        this.publisher = publisher;
        this.releaseDate = Date.from(releaseDate
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
