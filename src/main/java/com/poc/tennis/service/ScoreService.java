package com.poc.tennis.service;

import com.poc.tennis.business.GameRule;
import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Player;
import com.poc.tennis.model.Players;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class ScoreService {

    private final GameRule normalGameRule;
    private final GameRule tieBreakGameRule;

    /**
     * Method used to control and score point
     * @param game : Actual game played
     * @return Scoring player
     */
    public Players scorePoint(Game game){
        Player playerOne = game.getPlayersInGame().get(0);
        Player playerTwo = game.getPlayersInGame().get(1);
        if(Players.PLAYER_ONE.equals(scoringPlayer())){
            computePointsAndControlAndScore(playerOne, playerTwo, game);
            return Players.PLAYER_ONE;
        }
        computePointsAndControlAndScore(playerTwo, playerOne, game);
        return Players.PLAYER_TWO;
    }

    private void computePointsAndControlAndScore(Player playerToScore, Player playerNotToScore, Game game){
        int pointPlayerToScore = game.getScorePlayer().get(playerToScore.getId()).getPoint();
        int pointPlayerNotScore = game.getScorePlayer().get(playerNotToScore.getId()).getPoint();
        normalGameRule.control(game, playerToScore, playerNotToScore, pointPlayerToScore, pointPlayerNotScore);
    }

    /**
     * Method used to control and score point in case of a tie break game
     * @param game : Actual game played
     * @return Scoring player
     */
    public Players scorePointTieBreak(Game game){
        Player playerOne = game.getPlayersInGame().get(0);
        Player playerTwo = game.getPlayersInGame().get(1);
        if(Players.PLAYER_ONE.equals(scoringPlayer())){
            computePointsAndControlAndScoreTieBreak(playerOne, playerTwo, game);
            return Players.PLAYER_ONE;
        }
        computePointsAndControlAndScoreTieBreak(playerTwo, playerOne, game);
        return Players.PLAYER_TWO;
    }

    private void computePointsAndControlAndScoreTieBreak(Player playerToScore, Player playerNotToScore, Game game){
        int pointPlayerToScoreTieBreak = game.getScorePlayerTieBreak().get(playerToScore.getId());
        int pointPlayerNotScoreTieBreak = game.getScorePlayerTieBreak().get(playerNotToScore.getId());
        tieBreakGameRule.control(game, playerToScore, playerNotToScore, pointPlayerToScoreTieBreak, pointPlayerNotScoreTieBreak);
    }

    private Players scoringPlayer(){
        int random = new Random().nextInt(1000);
        if(random%3 == 0){
            return Players.PLAYER_ONE;
        }else{
            return Players.PLAYER_TWO;
        }
    }
}
