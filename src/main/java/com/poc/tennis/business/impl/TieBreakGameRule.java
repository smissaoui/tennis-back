package com.poc.tennis.business.impl;

import com.poc.tennis.business.GameRule;
import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Player;
import org.springframework.stereotype.Component;

@Component
public class TieBreakGameRule implements GameRule {

    /**
     * Control tie-break game following the different business rule
     * @param game : Game played
     * @param playerToScore : Scoring player in this one ball played
     * @param playerNotScore : Other player that didn't score in this one ball played
     * @param pointPlayerToScoreTieBreak : Point of the scoring player in this ball played
     * @param pointPlayerNotScoreTieBreak : Point of the other player that didn't score in this ball played
     */
    @Override
    public void control(Game game, Player playerToScore, Player playerNotScore, int pointPlayerToScoreTieBreak, int pointPlayerNotScoreTieBreak) {
        if(pointPlayerToScoreTieBreak<6){
            scoreTieBreak(playerToScore, game);
        }else if(pointPlayerToScoreTieBreak == 6 && pointPlayerNotScoreTieBreak < 6){
            setGameStatus(game, playerToScore);
        }else if(pointPlayerToScoreTieBreak == 6 && pointPlayerNotScoreTieBreak == 6){
            scoreTieBreak(playerToScore, game);
        }else if(pointPlayerToScoreTieBreak > 6 && (pointPlayerToScoreTieBreak - pointPlayerNotScoreTieBreak) < 2){
            scoreTieBreak(playerToScore, game);
        }else if(pointPlayerToScoreTieBreak > 6 && (pointPlayerToScoreTieBreak - pointPlayerNotScoreTieBreak) == 2){
            setGameStatus(game, playerToScore);
        }
    }
}
