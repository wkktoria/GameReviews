package io.github.wkktoria.game_reviews.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDate;
import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.wkktoria.game_reviews.dtos.GameDto;
import io.github.wkktoria.game_reviews.dtos.GameResponse;
import io.github.wkktoria.game_reviews.services.GameService;

@WebMvcTest(controllers = GameController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class GameControllerTests {

    @TestConfiguration
    static class TestConfig {

        @Bean
        GameService gameService() {
            return Mockito.mock(GameService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GameService gameService;
    @Autowired
    private ObjectMapper objectMapper;

    private GameDto gameDto;

    @BeforeEach
    public void init() {
        gameDto = GameDto.builder()
                .name("Name")
                .developer("Developer")
                .publisher("Publisher")
                .releaseDate(LocalDate.now())
                .build();
    }

    @Test
    public void GameController_GetGames_ReturnsGameResponse() throws Exception {
        // Arrange
        GameResponse responseDto = GameResponse.builder()
                .content(Arrays.asList(gameDto))
                .pageNumber(1)
                .pageSize(10)
                .totalElements(1)
                .totalPages(1)
                .last(true)
                .build();
        when(gameService.getAllGame(anyInt(), anyInt())).thenReturn(responseDto);

        // Act
        ResultActions response = mockMvc.perform(get("/api/game")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNumber", "1")
                .param("pageSize", "10"));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()",
                        CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    public void GameController_GameDetail_ReturnsGameDto() throws Exception {
        // Arrange
        when(gameService.getGameById(anyInt())).thenReturn(gameDto);

        // Act
        ResultActions response = mockMvc.perform(get("/api/game/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDto)));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(gameDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.developer", CoreMatchers.is(gameDto.getDeveloper())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher", CoreMatchers.is(gameDto.getPublisher())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate",
                        CoreMatchers.containsString(gameDto.getReleaseDate().toString())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void GameController_CreateGame_ReturnsCreated() throws Exception {
        // Arrange
        given(gameService.createGame(ArgumentMatchers.any()))
                .willAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResultActions response = mockMvc.perform(post("/api/game/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDto)));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(gameDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.developer", CoreMatchers.is(gameDto.getDeveloper())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher", CoreMatchers.is(gameDto.getPublisher())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate",
                        CoreMatchers.containsString(gameDto.getReleaseDate().toString())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void GameController_UpdateGame_ReturnsGameDto() throws Exception {
        // Arrange
        when(gameService.updateGame(any(GameDto.class), anyInt())).thenReturn(gameDto);

        // Act
        ResultActions response = mockMvc.perform(put("/api/game/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDto)));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(gameDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.developer", CoreMatchers.is(gameDto.getDeveloper())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher", CoreMatchers.is(gameDto.getPublisher())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate",
                        CoreMatchers.containsString(gameDto.getReleaseDate().toString())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void GameController_DeleteGame_ReturnsString() throws Exception {
        // Arrange
        doNothing().when(gameService).deleteGame(anyInt());

        // Act
        ResultActions response = mockMvc.perform(delete("/api/game/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
