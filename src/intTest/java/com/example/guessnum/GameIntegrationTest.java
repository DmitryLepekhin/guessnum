package com.example.guessnum;

import com.example.guessnum.game.GameLogic;
import com.example.guessnum.message.Message;
import com.example.guessnum.message.MessageConverter;
import com.example.guessnum.message.model.BidMessage;
import com.example.guessnum.message.model.GreetingMessage;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // a game must be started manually, the heartbeat is disabled
public class GameIntegrationTest {

    @Autowired
    WebSocketClient client;

    @Autowired
    MessageConverter messageConverter;

    @Autowired
    GameLogic gameLogic;

    @LocalServerPort
    int port;


    private volatile String lastMessage;

    private final CountDownLatch latch = new CountDownLatch(10);

    private final Logger logger = LoggerFactory.getLogger(GameIntegrationTest.class);

    @Test
    public void testOneGameRound() throws InterruptedException, ExecutionException, URISyntaxException, IOException {
        // start a game
        gameLogic.relaunch();

        // ten players stake different numbers covering the whole range
        // - one of them must win
        for (int i = 1; i <= 10; i++) {
            PlayerSession playerSession = new PlayerSession("name" + i);
            playerSession.connect();
            playerSession.greet();
            playerSession.bid(i, i * 11);
        }

        // give the messages time to be processed
        pause(3000);

        // finish the game
        gameLogic.relaunch();

        // wait for all "game over" messages
        latch.await(60, TimeUnit.SECONDS);

        // everyone gets the message
        assertEquals(0, latch.getCount());
        // check that there is one winner
        assertTrue(lastMessage.contains("winners = name"));
    }

    class PlayerSession {
        WebSocketSession session;
        String name;
        Handler handler;

        public PlayerSession(String name) {
            this.name = name;
            handler = new Handler();
        }

        public void connect() throws InterruptedException, ExecutionException, URISyntaxException {
            URI uri = new URI("ws://localhost:" + port);
            ListenableFuture<WebSocketSession> future = client.doHandshake(handler, null, uri);
            session = future.get();
        }

        public void greet() throws IOException {
            GreetingMessage message = new GreetingMessage(name);
            send(session, message);
        }

        public void bid(int num, int amount) throws IOException {
            BidMessage message = new BidMessage(num, amount);
            send(session, message);
        }

        private void send(WebSocketSession session, Message message) throws IOException {
            TextMessage msg = new TextMessage(messageConverter.serialize(message));
            session.sendMessage(msg);
        }
    }

    public class Handler extends TextWebSocketHandler {
        public void handleTextMessage(WebSocketSession session, TextMessage message) {
            logger.info(message.getPayload());
            lastMessage = message.getPayload();
            latch.countDown();
        }
    }

    private synchronized void pause(long ms) throws InterruptedException {
        wait(ms);
    }

}
