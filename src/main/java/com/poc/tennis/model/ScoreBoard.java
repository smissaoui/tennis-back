package com.poc.tennis.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ScoreBoard {
    List<String> headerScoreBoard;
    private String playerName1;
    private Score actualScorePlayer1;
    List<Integer> gamesWonByPlayer1;
    private String playerName2;
    private Score actualScorePlayer2;
    List<Integer> gamesWonByPlayer2;
    private Status matchWinner;
}

