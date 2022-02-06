package com.poc.tennis.service;

import com.poc.tennis.business.SetRule;
import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Set;
import com.poc.tennis.model.Players;
import com.poc.tennis.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.poc.tennis.model.Status.PLAYER1_WIN;
import static com.poc.tennis.model.Status.PLAYER2_WIN;

@Service
@AllArgsConstructor
public class SetService {

    private final SetRule normalSetRule;

    /**
     * Method used to handle set (control sets, create new sets or game...)
     * @param actualGame : Actual game played
     * @param scoringPlayer : Scoring player in this one ball played
     * @param actualSet : Actual set played
     */
    public void handleSet(Game actualGame, Players scoringPlayer, Set actualSet){
        if(actualGame.getGameStatus().getStatus() == scoringPlayer.getPlayerNumber()){
            Map<Status, List<Game>> gamesMapByStatus =  divideGamesByStatus(actualSet);
            int playerOneGameWins = computePlayerWinGames(PLAYER1_WIN, gamesMapByStatus);
            int playerTwoGameWins = computePlayerWinGames(PLAYER2_WIN, gamesMapByStatus);
            controlSet(scoringPlayer, actualSet, playerOneGameWins, playerTwoGameWins);
        }
    }

    private void controlSet(Players scoringPlayer, Set actualSet, int playerOneGameWins, int playerTwoGameWins){
        if(scoringPlayer.equals(Players.PLAYER_ONE)){
            normalSetRule.control(playerOneGameWins, playerTwoGameWins, actualSet, Players.PLAYER_ONE);
        }else{
            normalSetRule.control(playerTwoGameWins, playerOneGameWins, actualSet, Players.PLAYER_TWO);
        }
    }

    private int computePlayerWinGames(Status status, Map<Status, List<Game>> gamesMapByStatus){
        return gamesMapByStatus.get(status) != null ? gamesMapByStatus.get(status).size() : 0;
    }

    private Map<Status, List<Game>> divideGamesByStatus(Set actualSet){
        return actualSet.getGame()
                .stream()
                .collect(Collectors.groupingBy(Game::getGameStatus));
    }

}
