package com.poc.tennis.service;

import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Match;
import com.poc.tennis.entity.Set;
import com.poc.tennis.exception.MatchException;
import com.poc.tennis.model.MatchRequest;
import com.poc.tennis.model.Players;
import com.poc.tennis.model.ScoreBoard;
import com.poc.tennis.repository.MatchJpaRepository;
import com.poc.tennis.utils.Utils;
import com.poc.tennis.utils.UtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MatchServiceTest {

    @Mock
    private ScoreService scoreService;
    @Mock
    private SetService setService;
    @Mock
    private ScoreBoardService scoreBoardService;
    @Mock
    private MatchJpaRepository matchRepository;
    private MatchService matchService;

    @BeforeEach
    void init(){
        matchService = new MatchService(scoreService, setService, scoreBoardService, matchRepository);
    }

    @Test
    public void create_match_test(){
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);

        when(matchRepository.save(any(Match.class))).thenReturn(match);

        String result = matchService.createMatch(matchRequest);

        verify(matchRepository).save(any(Match.class));

        assertThat(result).isEqualTo(match.getId());
    }

    @Test
    public void play_one_ball_normal_game_test() throws Exception {
        String idMatch = "1";
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        ScoreBoard scoreBoard = UtilsTest.buildScoreBoard();

        when(matchRepository.findById(anyString())).thenReturn(Optional.of(match));
        when(scoreService.scorePoint(any(Game.class))).thenReturn(Players.PLAYER_ONE);
        when(scoreBoardService.computeScoreBoard(any(Game.class), any(Match.class))).thenReturn(scoreBoard);

        ScoreBoard result = matchService.playOneBall(idMatch);

        verify(matchRepository).findById(anyString());
        verify(scoreService).scorePoint(any(Game.class));
        verify(setService).handleSet(any(Game.class), any(Players.class), any(Set.class));
        matchRepository.save(match);
        verify(scoreBoardService).computeScoreBoard(any(Game.class), any(Match.class));

        assertThat(result).isEqualTo(scoreBoard);

    }

    @Test
    public void play_one_ball_tie_break_game_test() throws Exception {
        String idMatch = "1";
        MatchRequest matchRequest = UtilsTest.buildMatchRequest();
        Match match = Utils.buildMatch(matchRequest);
        match.getSets().get(0).setTieBreak(true);
        ScoreBoard scoreBoard = UtilsTest.buildScoreBoard();

        when(matchRepository.findById(anyString())).thenReturn(Optional.of(match));
        when(scoreService.scorePointTieBreak(any(Game.class))).thenReturn(Players.PLAYER_ONE);
        when(scoreBoardService.computeScoreBoard(any(Game.class), any(Match.class))).thenReturn(scoreBoard);

        ScoreBoard result = matchService.playOneBall(idMatch);

        verify(matchRepository).findById(anyString());
        verify(scoreService).scorePointTieBreak(any(Game.class));
        verify(setService).handleSet(any(Game.class), any(Players.class), any(Set.class));
        matchRepository.save(match);
        verify(scoreBoardService).computeScoreBoard(any(Game.class), any(Match.class));

        assertThat(result).isEqualTo(scoreBoard);
    }

    @Test
    public void play_one_ball_match_exception_test() {
        String idMatch = "1";

        when(matchRepository.findById(anyString())).thenReturn(Optional.empty());


        assertThrows(MatchException.class, () -> {
            matchService.playOneBall(idMatch);
        });

        verify(matchRepository).findById(anyString());

    }
}
