package com.poc.tennis.business.impl;

import com.poc.tennis.business.SetRule;
import com.poc.tennis.entity.Match;
import com.poc.tennis.entity.Set;
import com.poc.tennis.model.MatchRequest;
import com.poc.tennis.model.Players;
import com.poc.tennis.model.Status;
import com.poc.tennis.utils.Utils;
import com.poc.tennis.utils.UtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NormalSetRuleTest {

    private SetRule setRule;

    @BeforeEach
    void init(){
        setRule = new NormalSetRule();
    }

    @Test
    public void control_scoring_player_win_games_less_than_6_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Set actualSet = match.getSets().get(0);
        int scoringPlayerWinGames = 0;
        int otherPlayerWinGames = 0;
        Players potentialWinningPlayer = Players.PLAYER_ONE;

        setRule.control(scoringPlayerWinGames, otherPlayerWinGames, actualSet, potentialWinningPlayer);

        assertThat(actualSet.getGame().size()).isEqualTo(2);
    }

    @Test
    public void control_scoring_player_win_games_eq_6_and_other_player_lt_5_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Set actualSet = match.getSets().get(0);
        int scoringPlayerWinGames = 6;
        int otherPlayerWinGames = 4;
        Players potentialWinningPlayer = Players.PLAYER_ONE;

        setRule.control(scoringPlayerWinGames, otherPlayerWinGames, actualSet, potentialWinningPlayer);

        assertThat(actualSet.getSetStatus()).isEqualTo(Status.PLAYER1_WIN);
    }

    @Test
    public void control_scoring_player_win_games_eq_7_and_other_player_eq_5_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Set actualSet = match.getSets().get(0);
        int scoringPlayerWinGames = 7;
        int otherPlayerWinGames = 5;
        Players potentialWinningPlayer = Players.PLAYER_ONE;

        setRule.control(scoringPlayerWinGames, otherPlayerWinGames, actualSet, potentialWinningPlayer);

        assertThat(actualSet.getSetStatus()).isEqualTo(Status.PLAYER1_WIN);
    }

    @Test
    public void control_scoring_player_win_games_eq_6_and_other_player_eq_6_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Set actualSet = match.getSets().get(0);
        int scoringPlayerWinGames = 6;
        int otherPlayerWinGames = 6;
        Players potentialWinningPlayer = Players.PLAYER_ONE;

        setRule.control(scoringPlayerWinGames, otherPlayerWinGames, actualSet, potentialWinningPlayer);

        assertThat(actualSet.getGame().size()).isEqualTo(2);
        assertThat(actualSet.isTieBreak()).isTrue();
    }

    @Test
    public void control_scoring_player_win_games_eq_7_and_other_player_eq_6_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Set actualSet = match.getSets().get(0);
        int scoringPlayerWinGames = 7;
        int otherPlayerWinGames = 6;
        Players potentialWinningPlayer = Players.PLAYER_ONE;

        setRule.control(scoringPlayerWinGames, otherPlayerWinGames, actualSet, potentialWinningPlayer);

        assertThat(actualSet.getSetStatus()).isEqualTo(Status.PLAYER1_WIN);
    }
}
