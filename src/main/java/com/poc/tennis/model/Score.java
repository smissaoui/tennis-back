package com.poc.tennis.model;

public enum Score {

    ZERO(0),
    FIFTEEN(1),
    THIRTY(2),
    FORTY(3),
    ADVANTAGE(4);

    private int point;

    Score(int point){
        this.point = point;
    }

    public int getPoint(){
        return point;
    }

    public Score next(){
        return values()[ordinal() + 1];
    }

    public Score previous(){
        return values()[ordinal() - 1];
    }
}
