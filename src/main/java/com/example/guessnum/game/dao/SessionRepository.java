package com.example.guessnum.game.dao;

import com.example.guessnum.model.Session;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionRepository {

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public void save(Session session) {
        sessions.put(session.getSessionId(), session);
    }

    public void delete(String sessionId) {
        sessions.remove(sessionId);
    }

    public Session get(String sessionId) {
        return sessions.get(sessionId);
    }

}
