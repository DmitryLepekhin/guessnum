package com.example.guessnum;

import com.example.guessnum.game.GameLogic;
import com.example.guessnum.game.dao.GameRepository;
import com.example.guessnum.game.dao.SessionRepository;
import com.example.guessnum.message.model.BidMessage;
import com.example.guessnum.message.model.GreetingMessage;
import com.example.guessnum.model.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameLogicTest {

    @Autowired
    GameLogic gameLogic;

    @MockBean
    SessionRepository sessionRepository;

    @MockBean
    GameRepository gameRepository;

    @Test
    public void testDispatchGreeting_SessionSaved() {
        GreetingMessage message = new GreetingMessage("James");
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        when(webSocketSession.getId()).thenReturn("abc");
        gameLogic.dispatch(webSocketSession, message);
        verify(sessionRepository).save(any());
    }

    @Test
    public void testDispatchBid_AfterGreeting() {
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        when(webSocketSession.getId()).thenReturn("abc");

        Session session = new Session(webSocketSession, "James");
        given(sessionRepository.get("abc")).willReturn(session);


        BidMessage bidMessage = new BidMessage(5, 12);
        gameLogic.dispatch(webSocketSession, bidMessage);

        verify(gameRepository).bid("abc", 5, 12);
    }

    @Test
    public void testDispatchBid_IgnoredWithoutGreeting() {
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        when(webSocketSession.getId()).thenReturn("abc");

        // there has been no Greeting
        given(sessionRepository.get("abc")).willReturn(null);


        BidMessage bidMessage = new BidMessage(5, 12);
        gameLogic.dispatch(webSocketSession, bidMessage);

        verify(gameRepository, never()).bid("abc", 5, 12);
    }
}
