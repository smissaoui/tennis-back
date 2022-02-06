package com.poc.tennis.business.impl;

import com.poc.tennis.business.GameRule;
import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Player;
import org.springframework.stereotype.Component;

@Component
public class NormalGameRule implements GameRule {

    /**
     * Control game following the different business rule
     * @param game : Game played
     * @param playerToScore : Scoring player in this one ball played
     * @param playerNotScore : Other player that didn't score in this one ball played
     * @param pointPlayerToScore : Point of the scoring player in this ball played
     * @param pointPlayerNotScore : Point of the other player that didn't score in this ball played
     */
    @Override
    public void control(Game game, Player playerToScore, Player playerNotScore, int pointPlayerToScore, int pointPlayerNotScore) {
        if(pointPlayerToScore<3){
            score(playerToScore, game);
        }else if(pointPlayerToScore == 3 && pointPlayerNotScore < 3){
            //win game
            setGameStatus(game, playerToScore);
        }else if(pointPlayerToScore == 3 && pointPlayerNotScore == 3){
            //advantage
            score(playerToScore, game);
        }else if(pointPlayerToScore == 3 && pointPlayerNotScore == 4){
            //advantage
            score(playerToScore, game);
            previousScore(playerNotScore, game);
        }else if(pointPlayerToScore == 4 && pointPlayerNotScore == 3){
            //win game
            setGameStatus(game, playerToScore);
        }
    }
}
