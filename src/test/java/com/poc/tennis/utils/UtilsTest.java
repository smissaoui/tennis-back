package com.poc.tennis.utils;

import com.poc.tennis.model.MatchRequest;
import com.poc.tennis.model.Score;
import com.poc.tennis.model.ScoreBoard;
import com.poc.tennis.model.Status;
import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class UtilsTest {

    public MatchRequest buildMatchRequest(){
        return MatchRequest.builder()
                .playerOneName("player1")
                .playerTwoName("player2")
                .build();
    }

    public ScoreBoard buildScoreBoard(){
        return ScoreBoard.builder()
                .headerScoreBoard(Arrays.asList("Player", "test"))
                .playerName1("player1")
                .actualScorePlayer1(Score.ZERO)
                .gamesWonByPlayer1(Arrays.asList(2,3,4))
                .playerName2("player2")
                .actualScorePlayer2(Score.FIFTEEN)
                .gamesWonByPlayer2(Arrays.asList(1,2,5))
                .matchWinner(Status.ON_GOING)
                .build();
    }
}
