package com.poc.tennis.service;

import com.poc.tennis.business.SetRule;
import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Match;
import com.poc.tennis.entity.Set;
import com.poc.tennis.model.MatchRequest;
import com.poc.tennis.model.Players;
import com.poc.tennis.utils.Utils;
import com.poc.tennis.utils.UtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.poc.tennis.model.Status.PLAYER1_WIN;
import static com.poc.tennis.model.Status.PLAYER2_WIN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class SetServiceTest {

    @Mock
    private SetRule normalSetRule;

    private SetService setService;

    @BeforeEach
    void init(){
        setService = new SetService(normalSetRule);
    }

    @Test
    public void handle_set_player_one_scoring_player_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Set set = match.getSets().get(0);
        Game game = match.getSets().get(0).getGame().get(0);
        game.setGameStatus(PLAYER1_WIN);

        setService.handleSet(game, Players.PLAYER_ONE, set);

        verify(normalSetRule).control(anyInt(), anyInt(), any(Set.class), any(Players.class));
    }

    @Test
    public void handle_set_player_two_scoring_player_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Set set = match.getSets().get(0);
        Game game = match.getSets().get(0).getGame().get(0);
        game.setGameStatus(PLAYER2_WIN);

        setService.handleSet(game, Players.PLAYER_TWO, set);

        verify(normalSetRule).control(anyInt(), anyInt(), any(Set.class), any(Players.class));
    }
}
