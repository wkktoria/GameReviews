package io.github.wkktoria.game_reviews.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDto {

    private int id;
    private String title;
    private String content;
    private int stars;
}
