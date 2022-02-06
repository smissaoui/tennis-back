package com.poc.tennis.service;

import com.poc.tennis.entity.Game;
import com.poc.tennis.entity.Match;
import com.poc.tennis.model.ScoreBoard;
import com.poc.tennis.repository.SetJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.poc.tennis.model.Status.PLAYER1_WIN;
import static com.poc.tennis.model.Status.PLAYER2_WIN;
import static com.poc.tennis.utils.Constant.PLAYERS;
import static com.poc.tennis.utils.Constant.SET;
import static com.poc.tennis.utils.Constant.POINT;

@Service
@AllArgsConstructor
public class ScoreBoardService {

    private final SetJpaRepository setRepository;

    /**
     * Method used to compute the score board after each ball played and scored
     * @param actualGame : Actual game being played
     * @param match : Actual match being played
     * @return Object representing the state of the tennis match scoreboard
     */
    public ScoreBoard computeScoreBoard(Game actualGame, Match match){
        int setTotalNumber = setRepository.computeSetTotalNumber(match);
        List<String> headerScoreBoard = new ArrayList<>(Arrays.asList(PLAYERS, POINT));
        List<Integer> listGamesWonByPlayer1List = new ArrayList<>();
        List<Integer> listGamesWonByPlayer2 = new ArrayList<>();

        constructListSet(setTotalNumber, match, headerScoreBoard, listGamesWonByPlayer1List, listGamesWonByPlayer2);
        return  buildScoreBoard(actualGame, match, headerScoreBoard, listGamesWonByPlayer1List, listGamesWonByPlayer2);
    }

    private void constructListSet(int setTotalNumber, Match match, List<String> headerScoreBoard, List<Integer> listGamesWonByPlayer1List, List<Integer> listGamesWonByPlayer2){
        for(int i=0; i<setTotalNumber;i++){
            int setNumber = i+1;
            headerScoreBoard.add(SET + setNumber);
            int gamesWonByPlayer1 = setRepository.computeGamesWonByPlayer(i+1, PLAYER1_WIN, match);
            listGamesWonByPlayer1List.add(gamesWonByPlayer1);
            int gamesWonByPlayer2 = setRepository.computeGamesWonByPlayer(i+1, PLAYER2_WIN, match);
            listGamesWonByPlayer2.add(gamesWonByPlayer2);
        }
    }

    private ScoreBoard buildScoreBoard(Game actualGame, Match match, List<String> headerScoreBoard, List<Integer> listGamesWonByPlayer1List, List<Integer> listGamesWonByPlayer2){
        return ScoreBoard.builder()
                .headerScoreBoard(headerScoreBoard)
                .actualScorePlayer1(actualGame.getScorePlayer().get(match.getPlayers().get(0).getId()))
                .actualScorePlayer2(actualGame.getScorePlayer().get(match.getPlayers().get(1).getId()))
                .playerName1(actualGame.getPlayersInGame().get(0).getName())
                .playerName2(actualGame.getPlayersInGame().get(1).getName())
                .gamesWonByPlayer1(listGamesWonByPlayer1List)
                .gamesWonByPlayer2(listGamesWonByPlayer2)
                .matchWinner(match.getMatchStatus()).build();
    }
}
