package com.poc.tennis.service;

import com.poc.tennis.exception.GameException;
import com.poc.tennis.exception.MatchException;
import com.poc.tennis.exception.SetException;
import com.poc.tennis.model.*;
import com.poc.tennis.utils.Utils;
import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Match;
import com.poc.tennis.entity.Set;
import com.poc.tennis.repository.MatchJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.poc.tennis.utils.Constant.*;

@Service
@AllArgsConstructor
@Slf4j
public class MatchService {

    private final ScoreService scoreService;
    private final SetService setService;
    private final ScoreBoardService scoreBoardService;
    private final MatchJpaRepository matchRepository;

    /**
     * Service used to create a match of tennis
     * @param matchRequest : Parameter containing the names of the players
     * @return Id of the match created
     */
    public String createMatch(MatchRequest matchRequest){
        Match match = Utils.buildMatch(matchRequest);
        return matchRepository.save(match).getId();
    }

    /**
     * Service used to play one ball of tennis and score and control sets and games
     * @param idMatch : Id of the match played
     * @return Object representing the scoreBoard
     * @throws Exception
     */
    public ScoreBoard playOneBall(String idMatch) throws Exception {
        Optional<Match> matchOptional = matchRepository.findById(idMatch);
        if(matchOptional.isPresent()){
            Match match = matchOptional.get();
            Set actualSet = getActualSet(match);
            Game actualGame = getActualGame(actualSet);
            setInitialStatus(match, actualSet, actualGame);
            Players scoringPlayer = callScoreService(actualSet, actualGame);
            log.info(PLAYER_SCORED, scoringPlayer.getPlayerNumber());
            setService.handleSet(actualGame, scoringPlayer, actualSet);
            matchRepository.save(match);
            return scoreBoardService.computeScoreBoard(actualGame, match);
        }
        throw new MatchException(MATCH_EXCEPTION_MESSAGE + idMatch);
    }

    private Players callScoreService(Set actualSet, Game actualGame){
        if(!actualSet.isTieBreak()){
            return scoreService.scorePoint(actualGame);
        }
        return scoreService.scorePointTieBreak(actualGame);

    }

    private void setInitialStatus(Match match, Set set, Game game){
        setInitialMatchStatus(match);
        setInitialSetStatus(set);
        setInitialGameStatus(game);

    }

    private void setInitialMatchStatus(Match match){
        if(Status.NONE.equals(match.getMatchStatus())) {
            match.setMatchStatus(Status.ON_GOING);
        }
    }

    private void setInitialSetStatus(Set set){
        if(Status.NONE.equals(set.getSetStatus())) {
            set.setSetStatus(Status.ON_GOING);
        }
    }

    private void setInitialGameStatus(Game game){
        if(Status.NONE.equals(game.getGameStatus())) {
            game.setGameStatus(Status.ON_GOING);
        }
    }

    private Set getActualSet(Match match) throws SetException {
        return match.getSets()
                .stream()
                .filter(this::setStatusPredicate)
                .findFirst()
                .orElseThrow(() -> new SetException(SET_EXCEPTION_MESSAGE));
    }

    private Game getActualGame(Set actualSet) throws GameException {
        return actualSet.getGame()
                .stream()
                .filter(this::gameStatusPredicate)
                .findFirst()
                .orElseThrow(() -> new GameException(GAME_EXCEPTION_MESSAGE));
    }

    private boolean gameStatusPredicate(Game game){
        return game.getGameStatus().equals(Status.ON_GOING) ||
                game.getGameStatus().equals(Status.NONE);
    }

    private boolean setStatusPredicate(Set set){
        return Status.ON_GOING.equals(set.getSetStatus()) ||
                Status.NONE.equals(set.getSetStatus());
    }
}
