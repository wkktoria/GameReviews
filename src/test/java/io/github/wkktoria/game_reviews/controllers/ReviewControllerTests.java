package io.github.wkktoria.game_reviews.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.wkktoria.game_reviews.dtos.ReviewDto;
import io.github.wkktoria.game_reviews.services.ReviewService;

@WebMvcTest(controllers = ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ReviewControllerTests {

    @TestConfiguration
    static class TestConfig {

        @Bean
        ReviewService reviewService() {
            return Mockito.mock(ReviewService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ObjectMapper objectMapper;

    private ReviewDto reviewDto;

    @BeforeEach
    public void init() {
        reviewDto = ReviewDto.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();
    }

    @Test
    public void ReviewController_CreateReview_ReturnsReviewDto() throws Exception {
        // Arrange
        when(reviewService.createReview(anyInt(), any(ReviewDto.class))).thenReturn(reviewDto);

        // Act
        ResultActions response = mockMvc.perform(post("/api/game/1/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }

    @Test
    public void ReviewController_GetReviewsByGameId_ReturnsReviewDto() throws Exception {
        // Arrange
        when(reviewService.getReviewsByGameId(anyInt())).thenReturn(Arrays.asList(reviewDto));

        // Act
        ResultActions response = mockMvc.perform(get("/api/game/1/reviews")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.size()",
                                CoreMatchers.is(Arrays.asList(reviewDto).size())));
    }

    @Test
    public void ReviewController_GetReviewById_ReturnsReviewDto() throws Exception {
        // Arrange
        when(reviewService.getReviewById(anyInt(), anyInt())).thenReturn(reviewDto);

        // Act
        ResultActions response = mockMvc.perform(get("/api/game/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }

    @Test
    public void ReviewController_UpdateReview_ReturnsReviewDto() throws Exception {
        // Arrange
        when(reviewService.updateReview(anyInt(), anyInt(), any(ReviewDto.class))).thenReturn(reviewDto);

        // Act
        ResultActions response = mockMvc.perform(put("/api/game/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }

    @Test
    public void ReviewController_DeleteReview_() throws Exception {
        // Arrange
        doNothing().when(reviewService).deleteReview(anyInt(), anyInt());

        // Act
        ResultActions response = mockMvc.perform(delete("/api/game/1/reviews/1"));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
