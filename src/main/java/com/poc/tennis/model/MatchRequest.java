package com.poc.tennis.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchRequest {

    private String playerOneName;
    private String playerTwoName;
}
