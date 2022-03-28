package com.example.guessnum.game;

import com.example.guessnum.game.dao.SessionRepository;
import com.example.guessnum.message.Message;
import com.example.guessnum.message.MessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class Communication {

    private final Logger logger = LoggerFactory.getLogger(Communication.class);

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    MessageConverter converter;

    public void broadcast(Stream<String> sessionIds, Message message) {
        String payload = converter.serialize(message);
        broadcast(sessionIds, payload);
    }

    public void broadcast(Stream<String> sessionIds, String payload) {
        WebSocketMessage message = new TextMessage(payload);
        sessionIds.map(sessionRepository::get)
                .filter(Objects::nonNull)
                .forEach(session -> sendOnce(session.getSession(), message));
    }

    private void sendOnce(WebSocketSession session, WebSocketMessage message) {
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            logger.error("Error sending message", e);
        }
    }
}
