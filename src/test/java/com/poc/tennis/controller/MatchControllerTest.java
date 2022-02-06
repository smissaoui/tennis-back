package com.poc.tennis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.tennis.model.MatchRequest;
import com.poc.tennis.model.ScoreBoard;
import com.poc.tennis.service.MatchService;
import com.poc.tennis.utils.UtilsTest;
import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchController.class)
@AutoConfigureMockMvc
public class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MatchService matchService;

    @Test
    public void create_match_test() throws Exception {
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();

        when(matchService.createMatch(any(MatchRequest.class))).thenReturn("1");

        MvcResult mvcResult = mockMvc.perform(post("/match/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(matchRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String idMatch = mvcResult.getResponse().getContentAsString();

        assertThat(idMatch).isEqualTo(Json.pretty("1"));
    }

    @Test
    public void play_one_ball_test() throws Exception {
        String idMatch = "1";

        ScoreBoard scoreBoard = UtilsTest.buildScoreBoard();

        when(matchService.playOneBall(anyString())).thenReturn(scoreBoard);

        MvcResult mvcResult = mockMvc.perform(post("/match/play-one-ball")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(idMatch)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String scoreBoardJson = mvcResult.getResponse().getContentAsString();
        ScoreBoard result = objectMapper.readValue(scoreBoardJson, ScoreBoard.class);

        assertThat(result).isEqualTo(scoreBoard);
    }
}
