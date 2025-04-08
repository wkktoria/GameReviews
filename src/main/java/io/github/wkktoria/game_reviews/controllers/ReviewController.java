package io.github.wkktoria.game_reviews.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.wkktoria.game_reviews.dtos.ReviewDto;
import io.github.wkktoria.game_reviews.services.ReviewService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/")
@Slf4j
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/game/{gameId}/review")
    public ResponseEntity<ReviewDto> createReview(@PathVariable int gameId, @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(gameId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("/game/{gameId}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsByGameId(@PathVariable int gameId) {
        return ResponseEntity.ok(reviewService.getReviewsByGameId(gameId));
    }
}
