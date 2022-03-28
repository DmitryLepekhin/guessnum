package com.example.guessnum.config;

import com.example.guessnum.game.GameLogic;
import com.example.guessnum.message.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class AppWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    MessageConverter messageConverter;

    @Autowired
    GameLogic gameLogic;

    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        messageConverter.parse(message.getPayload())
                .ifPresent(msg -> gameLogic.dispatch(session, msg));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        gameLogic.logout(session);
    }

}
