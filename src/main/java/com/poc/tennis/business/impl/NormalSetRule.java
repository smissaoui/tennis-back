package com.poc.tennis.business.impl;

import com.poc.tennis.business.SetRule;
import com.poc.tennis.entity.Set;
import com.poc.tennis.model.Players;
import org.springframework.stereotype.Component;

@Component
public class NormalSetRule implements SetRule {

    /**
     * Control set following the different business rules
     * @param scoringPlayerWinGames : Number of games won by the scoring player
     * @param otherPlayerWinGames : Number of games won by the other player
     * @param actualSet : Object representing the set being played
     * @param potentialWinningPlayer : Scoring player that can be the potential winner
     */
    @Override
    public void control(int scoringPlayerWinGames, int otherPlayerWinGames, Set actualSet, Players potentialWinningPlayer) {
        if(scoringPlayerWinGames <6){
            newGame(actualSet);
        }else if(scoringPlayerWinGames == 6 && otherPlayerWinGames <5){
            winSetHandler(actualSet, potentialWinningPlayer);
        }else if(scoringPlayerWinGames == 7 && otherPlayerWinGames == 5){
            winSetHandler(actualSet, potentialWinningPlayer);
        }else if(scoringPlayerWinGames == 6 && otherPlayerWinGames == 6){
            // tie-break
            if(!actualSet.isTieBreak()){
                newGameTieBreak(actualSet);
                actualSet.setTieBreak(true);
            }
        }else if(scoringPlayerWinGames == 7 && otherPlayerWinGames == 6){
            winSetHandler(actualSet, potentialWinningPlayer);
        }
    }
}
