package com.poc.tennis.controller;

import com.poc.tennis.model.MatchRequest;
import com.poc.tennis.model.ScoreBoard;
import com.poc.tennis.service.MatchService;
import io.swagger.v3.core.util.Json;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.poc.tennis.utils.Constant.MATCH_CREATION;
import static com.poc.tennis.utils.Constant.PLAYING_ONE_BALL;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;

    /**
     * Controller used to create a match of tennis
     * @param matchRequest : Parameter containing the names of the players
     * @return Id of the match created
     */
    @PostMapping("/create")
    public String createMatch(@RequestBody MatchRequest matchRequest){
        log.info(MATCH_CREATION, matchRequest.getPlayerOneName(), matchRequest.getPlayerTwoName());
        return Json.pretty(matchService.createMatch(matchRequest));
    }

    /**
     * Controller used to play one ball of tennis and score
     * @param idMatch : Id of the match played
     * @return an object representing thr scoreBoard
     * @throws Exception
     */
    @PostMapping("/play-one-ball")
    public ScoreBoard playOneBall(@RequestBody String idMatch) throws Exception {
        log.info(PLAYING_ONE_BALL, idMatch);
        return matchService.playOneBall(idMatch);
    }
}
