package io.github.wkktoria.game_reviews.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameResponse {

    private List<GameDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
