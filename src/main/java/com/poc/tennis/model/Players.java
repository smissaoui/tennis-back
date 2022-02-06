package com.poc.tennis.model;

public enum Players {
    PLAYER_ONE(1),
    PLAYER_TWO(2);

    private final int playerNumber;

    Players(int playerNumber){
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber(){
        return playerNumber;
    }
}
