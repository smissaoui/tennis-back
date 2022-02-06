package com.poc.tennis.service;

import com.poc.tennis.business.GameRule;
import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Match;
import com.poc.tennis.entity.Player;
import com.poc.tennis.model.MatchRequest;
import com.poc.tennis.model.Players;
import com.poc.tennis.utils.Utils;
import com.poc.tennis.utils.UtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTest {

    @Mock
    private GameRule normalGameRule;
    @Mock
    private GameRule tieBreakGameRule;

    private ScoreService scoreService;

    @BeforeEach
    void init(){
        scoreService = new ScoreService(normalGameRule, tieBreakGameRule);
    }

    @Test
    public void score_point_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Game game = match.getSets().get(0).getGame().get(0);

        Players result = scoreService.scorePoint(game);

        verify(normalGameRule).control(any(Game.class), any(Player.class), any(Player.class), anyInt(), anyInt());

        assertThat(result).isIn(Players.PLAYER_ONE, Players.PLAYER_TWO);
    }

    @Test
    public void score_point_tie_break_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Game game = match.getSets().get(0).getGame().get(0);
        Player playerToScore = match.getPlayers().get(0);
        Player playerNotToScore = match.getPlayers().get(1);
        Map<String, Integer> scorePlayerTieBreak = new HashMap<>();
        scorePlayerTieBreak.put(playerToScore.getId(), 0);
        scorePlayerTieBreak.put(playerNotToScore.getId(), 0);
        game.setScorePlayerTieBreak(scorePlayerTieBreak);

        Players result = scoreService.scorePointTieBreak(game);

        verify(tieBreakGameRule).control(any(Game.class), any(Player.class), any(Player.class), anyInt(), anyInt());

        assertThat(result).isIn(Players.PLAYER_ONE, Players.PLAYER_TWO);
    }
}
