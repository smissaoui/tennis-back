package com.poc.tennis.business.impl;

import com.poc.tennis.business.GameRule;
import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Match;
import com.poc.tennis.entity.Player;
import com.poc.tennis.model.MatchRequest;
import com.poc.tennis.model.Score;
import com.poc.tennis.model.Status;
import com.poc.tennis.utils.Utils;
import com.poc.tennis.utils.UtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class NormalGameRuleTest {

   private GameRule gameRule;

    @BeforeEach
    void init(){
        gameRule = new NormalGameRule();
    }

    @Test
    public void control_point_player_to_score_less_than_3_test(){

        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Game game = match.getSets().get(0).getGame().get(0);
        Player playerToScore = match.getPlayers().get(0);
        Player playerNotToScore = match.getPlayers().get(1);
        int pointPlayerToScore = 2;
        int pointPlayerNotScore = 0;
        Map<String, Score> scorePlayer =  game.getScorePlayer();

        gameRule.control(game, playerToScore, playerNotToScore, pointPlayerToScore, pointPlayerNotScore);

        assertThat(scorePlayer.get(playerToScore.getId())).isEqualTo(Score.FIFTEEN);
    }

    @Test
    public void control_point_player_to_score_equal_3_and_other_player_lt_3_test(){

        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Game game = match.getSets().get(0).getGame().get(0);
        Player playerToScore = match.getPlayers().get(0);
        Player playerNotToScore = match.getPlayers().get(1);
        int pointPlayerToScore = 3;
        int pointPlayerNotScore = 0;
        Map<String, Score> scorePlayer =  game.getScorePlayer();

        gameRule.control(game, playerToScore, playerNotToScore, pointPlayerToScore, pointPlayerNotScore);

        assertThat(game.getGameStatus()).isEqualTo(Status.PLAYER1_WIN);
    }

    @Test
    public void control_point_two_players_to_score_equal_3_test(){

        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Game game = match.getSets().get(0).getGame().get(0);
        Player playerToScore = match.getPlayers().get(0);
        Player playerNotToScore = match.getPlayers().get(1);
        int pointPlayerToScore = 3;
        int pointPlayerNotScore = 3;
        Map<String, Score> scorePlayer =  game.getScorePlayer();
        scorePlayer.put(playerToScore.getId(), Score.FORTY);

        gameRule.control(game, playerToScore, playerNotToScore, pointPlayerToScore, pointPlayerNotScore);

        assertThat(scorePlayer.get(playerToScore.getId())).isEqualTo(Score.ADVANTAGE);
    }

    @Test
    public void control_point_player_to_score_equal_3_and_other_player_equal_4_test(){

        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Game game = match.getSets().get(0).getGame().get(0);
        Player playerToScore = match.getPlayers().get(0);
        Player playerNotToScore = match.getPlayers().get(1);
        int pointPlayerToScore = 3;
        int pointPlayerNotScore = 4;
        Map<String, Score> scorePlayer =  game.getScorePlayer();
        scorePlayer.put(playerToScore.getId(), Score.FORTY);
        scorePlayer.put(playerNotToScore.getId(), Score.ADVANTAGE);


        gameRule.control(game, playerToScore, playerNotToScore, pointPlayerToScore, pointPlayerNotScore);

        assertThat(scorePlayer.get(playerToScore.getId())).isEqualTo(Score.ADVANTAGE);
        assertThat(scorePlayer.get(playerNotToScore.getId())).isEqualTo(Score.FORTY);
    }

    @Test
    public void control_point_player_to_score_equal_4_and_other_player_equal_3_test(){

        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Game game = match.getSets().get(0).getGame().get(0);
        Player playerToScore = match.getPlayers().get(0);
        Player playerNotToScore = match.getPlayers().get(1);
        int pointPlayerToScore = 4;
        int pointPlayerNotScore = 3;
        Map<String, Score> scorePlayer =  game.getScorePlayer();
        scorePlayer.put(playerToScore.getId(), Score.ADVANTAGE);
        scorePlayer.put(playerNotToScore.getId(), Score.FORTY);


        gameRule.control(game, playerToScore, playerNotToScore, pointPlayerToScore, pointPlayerNotScore);

        assertThat(game.getGameStatus()).isEqualTo(Status.PLAYER1_WIN);
    }
}
