package com.poc.tennis.utils;

import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Match;
import com.poc.tennis.entity.Player;
import com.poc.tennis.entity.Set;
import com.poc.tennis.model.MatchRequest;
import com.poc.tennis.model.Players;
import com.poc.tennis.model.Score;
import com.poc.tennis.model.Status;
import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class Utils {

    public Match buildMatch(MatchRequest matchRequest){
        Player playerOne = buildPlayer(matchRequest.getPlayerOneName(), Players.PLAYER_ONE);
        Player playerTwo = buildPlayer(matchRequest.getPlayerTwoName(), Players.PLAYER_TWO);
        Set set = buildSet();
        Game game = buildGame();
        game.setSet(set);
        game.setPlayersInGame(Arrays.asList(playerOne, playerTwo));
        set.setGame(new ArrayList<>(Collections.singleton(game)));
        HashMap<String, Score> scorePlayer = new HashMap<>();
        scorePlayer.put(playerOne.getId(), Score.ZERO);
        scorePlayer.put(playerTwo.getId(), Score.ZERO);
        game.setScorePlayer(scorePlayer);

        Match match = Match.builder()
                .id(UUID.randomUUID().toString())
                .sets(new ArrayList<>(Collections.singleton(set)))
                .players(Arrays.asList(playerOne, playerTwo))
                .matchStatus(Status.NONE)
                .build();
        playerOne.setMatches(Collections.singletonList(match));
        playerTwo.setMatches(Collections.singletonList(match));
        playerOne.setGames(set.getGame());
        playerTwo.setGames(set.getGame());
        set.setMatch(match);
        return match;
    }


    public Set buildSet(){
        return Set.builder()
                .id(UUID.randomUUID().toString())
                .setStatus(Status.NONE)
                .setNumber(1)
                .game(new ArrayList<>())
                .build();
    }

    public Game buildGame(){
        return Game.builder()
                .id(UUID.randomUUID().toString())
                .gameStatus(Status.NONE)
                .build();
    }

    public Player buildPlayer(String name, Players tag){
        return Player.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .tag(tag)
                .build();
    }
}
