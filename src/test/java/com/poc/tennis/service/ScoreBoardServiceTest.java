package com.poc.tennis.service;

import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Match;
import com.poc.tennis.model.MatchRequest;
import com.poc.tennis.model.ScoreBoard;
import com.poc.tennis.model.Status;
import com.poc.tennis.repository.SetJpaRepository;
import com.poc.tennis.utils.Utils;
import com.poc.tennis.utils.UtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.poc.tennis.model.Status.PLAYER1_WIN;
import static com.poc.tennis.model.Status.PLAYER2_WIN;
import static com.poc.tennis.utils.Constant.PLAYERS;
import static com.poc.tennis.utils.Constant.POINT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ScoreBoardServiceTest {

    @Mock
    private SetJpaRepository setRepository;

    private ScoreBoardService scoreBoardService;

    @BeforeEach
    void init(){
        scoreBoardService = new ScoreBoardService(setRepository);
    }

    @Test
    public void compute_score_board_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        Game game = match.getSets().get(0).getGame().get(0);
        ScoreBoard expected = buildScoreBoard(game, match, new ArrayList<>(Arrays.asList(PLAYERS, POINT, "Set1")), List.of(1), List.of(2));

        when(setRepository.computeSetTotalNumber(any(Match.class))).thenReturn(1);
        when(setRepository.computeGamesWonByPlayer(1, PLAYER1_WIN, match)).thenReturn(1);
        when(setRepository.computeGamesWonByPlayer(1, PLAYER2_WIN, match)).thenReturn(2);

        ScoreBoard scoreBoard = scoreBoardService.computeScoreBoard(game, match);

        verify(setRepository).computeSetTotalNumber(any(Match.class));
        verify(setRepository, times(2)).computeGamesWonByPlayer(anyInt(), any(Status.class), any(Match.class));

        assertThat(scoreBoard).isEqualTo(expected);
    }

    private ScoreBoard buildScoreBoard(Game actualGame, Match match, List<String> headerScoreBoard, List<Integer> listGamesWonByPlayer1List, List<Integer> listGamesWonByPlayer2){
        return ScoreBoard.builder()
                .headerScoreBoard(headerScoreBoard)
                .actualScorePlayer1(actualGame.getScorePlayer().get(match.getPlayers().get(0).getId()))
                .actualScorePlayer2(actualGame.getScorePlayer().get(match.getPlayers().get(1).getId()))
                .playerName1(actualGame.getPlayersInGame().get(0).getName())
                .playerName2(actualGame.getPlayersInGame().get(1).getName())
                .gamesWonByPlayer1(listGamesWonByPlayer1List)
                .gamesWonByPlayer2(listGamesWonByPlayer2)
                .matchWinner(match.getMatchStatus()).build();
    }
}
