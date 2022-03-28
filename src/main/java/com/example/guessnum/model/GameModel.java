package com.example.guessnum.model;

import lombok.Getter;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class GameModel {

    private int secret;
    private ConcurrentHashMap<String, Player> players;

    public GameModel() {
        secret = new Random().nextInt(10) + 1;
        players = new ConcurrentHashMap<>();
    }

}
