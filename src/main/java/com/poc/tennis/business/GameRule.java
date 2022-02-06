package com.poc.tennis.business;

import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Player;
import com.poc.tennis.model.Players;
import com.poc.tennis.model.Score;
import com.poc.tennis.model.Status;

public interface GameRule {

    void control(Game game, Player playerToScore, Player playerNotScore, int pointPlayerToScore, int pointPlayerNotScore);

    default void setGameStatus(Game game, Player playerToScore){
        if(Players.PLAYER_ONE.equals(playerToScore.getTag())){
            game.setGameStatus(Status.PLAYER1_WIN);
        }else{
            game.setGameStatus(Status.PLAYER2_WIN);
        }
    }

    default void scoreTieBreak(Player player, Game game){
        int score = game.getScorePlayerTieBreak().get(player.getId());
        game.getScorePlayerTieBreak().put(player.getId(), score+1);
    }

    default void score(Player player, Game game){
        Score score = game.getScorePlayer().get(player.getId());
        game.getScorePlayer().put(player.getId(), score.next());
    }

    default void previousScore(Player player, Game game){
        Score score = game.getScorePlayer().get(player.getId());
        game.getScorePlayer().put(player.getId(), score.previous());
    }
}
