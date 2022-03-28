package com.example.guessnum.game;

import com.example.guessnum.game.dao.GameRepository;
import com.example.guessnum.game.dao.SessionRepository;
import com.example.guessnum.message.Message;
import com.example.guessnum.message.model.BidMessage;
import com.example.guessnum.message.model.GameOverMessage;
import com.example.guessnum.message.model.GreetingMessage;
import com.example.guessnum.model.GameModel;
import com.example.guessnum.model.Player;
import com.example.guessnum.model.Session;
import com.example.guessnum.model.Winner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class GameLogic {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    Communication communication;

    @Autowired
    WinCalculator winCalculator;

    public void dispatch(WebSocketSession session, Message message) {
        if (message instanceof GreetingMessage) {
            sessionRepository.save(
                    new Session(session, ((GreetingMessage)message).getName())
            );
        }
        if (message instanceof BidMessage) {
            // check that the name has been set
            if (sessionRepository.get(session.getId()) == null) {
                return;
            }
            BidMessage bidMessage = (BidMessage)message;
            gameRepository.bid(
                    session.getId(),
                    bidMessage.getNum(),
                    bidMessage.getBid()
            );
        }
    }

    public void relaunch() {
        GameModel model = gameRepository.getGame();
        if (model != null) {
            if (model.getPlayers().isEmpty()) {
                // do nothing, the game can be reused
                return;
            }
            finish(model);
        }
        gameRepository.setGame(new GameModel());
    }

    private void finish(GameModel game) {
        if (game == null) {
            return;
        }

        Collection<Winner> winners = game.getPlayers().values().stream()
                .filter(player -> player.getNum() == game.getSecret())
                .map(player -> {
                    String name = sessionRepository.get(player.getSessionId()).getName();
                    String win = winCalculator.calculate(player.getBid());
                    return new Winner(name, win);
                })
                .collect(Collectors.toList());

        communication.broadcast(game.getPlayers().values().stream().map(Player::getSessionId), new GameOverMessage(game.getSecret(), winners));
    }

    public void logout(WebSocketSession session) {
        sessionRepository.delete(session.getId());
    }

}
