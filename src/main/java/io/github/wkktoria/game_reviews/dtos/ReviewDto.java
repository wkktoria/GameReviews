package io.github.wkktoria.game_reviews.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ReviewDto {

    private int id;
    private String title;
    private String content;
    private int stars;
}
