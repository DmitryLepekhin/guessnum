package com.example.guessnum.game.quarz;

import com.example.guessnum.config.GuessnumProperties;
import com.example.guessnum.game.GameLogic;
import com.example.guessnum.game.dao.GameRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
@Profile("!test")
public class Heartbeat implements InitializingBean {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameLogic gameLogic;

    @Autowired
    GuessnumProperties props;

    public ScheduledFuture<?> relaunchGame() {
        final Runnable task = () -> gameLogic.relaunch();
        final ScheduledFuture<?> future =
                Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(task, 0, props.getPeriod(), SECONDS);
        return future;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        relaunchGame();
    }

}
