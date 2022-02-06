package com.poc.tennis.business.impl;

import com.poc.tennis.business.GameRule;
import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Match;
import com.poc.tennis.entity.Player;
import com.poc.tennis.model.MatchRequest;
import com.poc.tennis.model.Status;
import com.poc.tennis.utils.Utils;
import com.poc.tennis.utils.UtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TieBreakGameRuleTest {

    private GameRule gameRule;

    @BeforeEach
    void init(){
        gameRule = new TieBreakGameRule();
    }

    @Test
    public void control_point_player_to_score_tie_break_less_than_3_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Game game = match.getSets().get(0).getGame().get(0);
        Player playerToScore = match.getPlayers().get(0);
        Player playerNotToScore = match.getPlayers().get(1);
        int pointPlayerToScoreTieBreak = 2;
        int pointPlayerNotScoreTieBreak = 0;
        Map<String, Integer> scorePlayerTieBreak = new HashMap<>();
        scorePlayerTieBreak.put(playerToScore.getId(), 0);
        scorePlayerTieBreak.put(playerNotToScore.getId(), 0);
        game.setScorePlayerTieBreak(scorePlayerTieBreak);

        gameRule.control(game, playerToScore, playerNotToScore, pointPlayerToScoreTieBreak, pointPlayerNotScoreTieBreak);
        assertThat(scorePlayerTieBreak.get(playerToScore.getId())).isEqualTo(1);
    }

    @Test
    public void control_point_player_to_score_tie_break_equal_6_and_other_player_lt_6_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Game game = match.getSets().get(0).getGame().get(0);
        Player playerToScore = match.getPlayers().get(0);
        Player playerNotToScore = match.getPlayers().get(1);
        int pointPlayerToScoreTieBreak = 6;
        int pointPlayerNotScoreTieBreak = 2;
        Map<String, Integer> scorePlayerTieBreak = new HashMap<>();
        scorePlayerTieBreak.put(playerToScore.getId(), 6);
        scorePlayerTieBreak.put(playerNotToScore.getId(), 2);
        game.setScorePlayerTieBreak(scorePlayerTieBreak);

        gameRule.control(game, playerToScore, playerNotToScore, pointPlayerToScoreTieBreak, pointPlayerNotScoreTieBreak);
        assertThat(game.getGameStatus()).isEqualTo(Status.PLAYER1_WIN);
    }

    @Test
    public void control_point_player_to_score_tie_break_equal_6_and_other_player_equal_6_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Game game = match.getSets().get(0).getGame().get(0);
        Player playerToScore = match.getPlayers().get(0);
        Player playerNotToScore = match.getPlayers().get(1);
        int pointPlayerToScoreTieBreak = 6;
        int pointPlayerNotScoreTieBreak = 6;
        Map<String, Integer> scorePlayerTieBreak = new HashMap<>();
        scorePlayerTieBreak.put(playerToScore.getId(), 6);
        scorePlayerTieBreak.put(playerNotToScore.getId(), 6);
        game.setScorePlayerTieBreak(scorePlayerTieBreak);

        gameRule.control(game, playerToScore, playerNotToScore, pointPlayerToScoreTieBreak, pointPlayerNotScoreTieBreak);
        assertThat(scorePlayerTieBreak.get(playerToScore.getId())).isEqualTo(7);
    }

    @Test
    public void control_point_player_to_score_tie_break_mt_6_and_other_player_diff_lt_2_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Game game = match.getSets().get(0).getGame().get(0);
        Player playerToScore = match.getPlayers().get(0);
        Player playerNotToScore = match.getPlayers().get(1);
        int pointPlayerToScoreTieBreak = 7;
        int pointPlayerNotScoreTieBreak = 6;
        Map<String, Integer> scorePlayerTieBreak = new HashMap<>();
        scorePlayerTieBreak.put(playerToScore.getId(), 7);
        scorePlayerTieBreak.put(playerNotToScore.getId(), 6);
        game.setScorePlayerTieBreak(scorePlayerTieBreak);

        gameRule.control(game, playerToScore, playerNotToScore, pointPlayerToScoreTieBreak, pointPlayerNotScoreTieBreak);
        assertThat(scorePlayerTieBreak.get(playerToScore.getId())).isEqualTo(8);
    }

    @Test
    public void control_point_player_to_score_tie_break_mt_6_and_other_player_diff_equal_2_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Game game = match.getSets().get(0).getGame().get(0);
        Player playerToScore = match.getPlayers().get(0);
        Player playerNotToScore = match.getPlayers().get(1);
        int pointPlayerToScoreTieBreak = 8;
        int pointPlayerNotScoreTieBreak = 6;
        Map<String, Integer> scorePlayerTieBreak = new HashMap<>();
        scorePlayerTieBreak.put(playerToScore.getId(), 8);
        scorePlayerTieBreak.put(playerNotToScore.getId(), 6);
        game.setScorePlayerTieBreak(scorePlayerTieBreak);

        gameRule.control(game, playerToScore, playerNotToScore, pointPlayerToScoreTieBreak, pointPlayerNotScoreTieBreak);
        assertThat(game.getGameStatus()).isEqualTo(Status.PLAYER1_WIN);
    }
}
