package com.poc.tennis.business;

import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Match;
import com.poc.tennis.entity.Player;
import com.poc.tennis.entity.Set;
import com.poc.tennis.model.Players;
import com.poc.tennis.model.Score;
import com.poc.tennis.model.Status;
import com.poc.tennis.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.poc.tennis.model.Status.PLAYER1_WIN;
import static com.poc.tennis.model.Status.PLAYER2_WIN;
public interface SetRule {

    void control(int scoringPlayerWinGames, int otherPlayerWinGames, Set actualSet, Players potentialWinningPlayer);

    default void newGame(Set actualSet){
        Game newGame = Utils.buildGame();
        newGame.setSet(actualSet);
        newGame.setGameStatus(Status.ON_GOING);
        newGame.setPlayersInGame(actualSet.getMatch().getPlayers());
        HashMap<String, Score> scorePlayer = new HashMap<>();
        List<Player> players = actualSet.getMatch().getPlayers();
        scorePlayer.put(players.get(0).getId(), Score.ZERO);
        scorePlayer.put(players.get(1).getId(), Score.ZERO);
        newGame.setScorePlayer(scorePlayer);
        actualSet.getGame().add(newGame);
    }

    default void newGameTieBreak(Set actualSet){
        Game newGame = Utils.buildGame();
        newGame.setSet(actualSet);
        newGame.setGameStatus(Status.ON_GOING);
        newGame.setPlayersInGame(actualSet.getMatch().getPlayers());
        HashMap<String, Integer> scorePlayerTieBreak = new HashMap<>();
        List<Player> players = actualSet.getMatch().getPlayers();
        scorePlayerTieBreak.put(players.get(0).getId(), 0);
        scorePlayerTieBreak.put(players.get(1).getId(), 0);
        newGame.setScorePlayerTieBreak(scorePlayerTieBreak);
        actualSet.getGame().add(newGame);
    }

    default void winSetHandler(Set actualSet, Players potentialWinningPlayer){
        actualSet.setSetStatus(Status.values()[potentialWinningPlayer.getPlayerNumber() + 1]);
        findWinner(actualSet.getMatch());
        if(!doWinnerExist(actualSet)){
            Set newSet = createNewSet(actualSet);
            attachSetToMatch(actualSet, newSet);
            newGame(newSet);
        }
    }

    private void attachSetToMatch(Set actualSet, Set newSet){
        Match match = actualSet.getMatch();
        List<Set> sets = match.getSets();
        sets.add(newSet);
    }

    private void findWinner(Match match){
        List<Set> sets = match.getSets();
        Map<Status, List<Set>> setMapByStatus =  sets.stream()
                .collect(Collectors
                        .groupingBy(Set::getSetStatus));
        int playerOneSetWins = computePlayerSetWins(PLAYER1_WIN, setMapByStatus);
        int playerTwoSetWins = computePlayerSetWins(PLAYER2_WIN, setMapByStatus);
        computeWinner(match, playerOneSetWins, playerTwoSetWins);
    }

    private boolean doWinnerExist(Set actualSet){
        return Status.PLAYER1_WIN.equals(actualSet.getMatch().getMatchStatus()) || Status.PLAYER2_WIN.equals(actualSet.getMatch().getMatchStatus());
    }

    private int computePlayerSetWins(Status status, Map<Status, List<Set>> setMapByStatus){
        return setMapByStatus.get(status) != null ? setMapByStatus.get(status).size() : 0;
    }

    private Set createNewSet(Set actualSet){
        Set newSet = Utils.buildSet();
        newSet.setMatch(actualSet.getMatch());
        newSet.setSetStatus(Status.NONE);
        newSet.setSetNumber(actualSet.getSetNumber() + 1);
        return newSet;
    }

    private void computeWinner(Match match, int playerOneSetWins, int playerTwoSetWins){
        computeWinnerPlayer(match, playerOneSetWins, playerTwoSetWins, PLAYER1_WIN);
        computeWinnerPlayer(match, playerTwoSetWins, playerOneSetWins, PLAYER2_WIN);
    }


    private void computeWinnerPlayer(Match match, int playerSetWins1, int playerSetWins2, Status status){
        if(playerSetWins1 - playerSetWins2 == 2){
            match.setMatchStatus(status);
        }
    }
}
