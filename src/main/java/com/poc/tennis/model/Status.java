package com.poc.tennis.model;

public enum Status {
    NONE(-1),
    ON_GOING(0),
    PLAYER1_WIN(1),
    PLAYER2_WIN(2);

    private int status;

     Status(int status){
        this.status = status;
    }

    public int getStatus(){
        return status;
    }


}
