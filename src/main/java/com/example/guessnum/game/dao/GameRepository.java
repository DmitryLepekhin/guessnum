package com.example.guessnum.game.dao;

import com.example.guessnum.model.GameModel;
import com.example.guessnum.model.Player;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class GameRepository {

    AtomicReference<GameModel> game = new AtomicReference<>();

    public GameModel getGame() {
        return game.get();
    }

    public void setGame(GameModel game) {
        this.game.set(game);
    }

    public void bid(String sessionId, int num, int bid) {
        GameModel model = game.get();
        if (model == null) return;
        // all old bids are overridden by the new bid
        model.getPlayers().put(sessionId, new Player(sessionId, num, bid));
    }

}
