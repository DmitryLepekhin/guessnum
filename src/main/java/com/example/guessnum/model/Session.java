package com.example.guessnum.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Session {

    @NotNull
    private String sessionId;

    private WebSocketSession session;

    private String name;

    public Session(WebSocketSession session, String name) {
        this.session = session;
        this.name = name;
        sessionId = session.getId();
    }
}
