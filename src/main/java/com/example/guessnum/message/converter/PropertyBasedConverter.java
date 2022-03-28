package com.example.guessnum.message.converter;

import com.example.guessnum.message.Message;
import com.example.guessnum.message.MessageConverter;
import com.example.guessnum.message.model.BidMessage;
import com.example.guessnum.message.model.GameOverMessage;
import com.example.guessnum.message.model.GreetingMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;
import java.util.Properties;

@Service
public class PropertyBasedConverter implements MessageConverter {

    @Autowired
    private BidConverter bidConverter;

    @Autowired
    private GreetingConverter greetingConverter;

    @Autowired
    private GameOverConverter gameOverConverter;

    @Override
    public Optional<Message> parse(String payload) {
        try {
            Properties props = new Properties();
            props.load(new StringReader(payload));

            Optional<Message> result = bidConverter.parse(props);

            if (!result.isPresent()) {
                result = greetingConverter.parse(props);
            }

            return result;
        } catch (IOException e) {
            // just ignore the incorrect message
            return Optional.empty();
        }
    }

    @Override
    public String serialize(Message message) {
        if (message instanceof BidMessage) {
            return bidConverter.getPayload((BidMessage) message);
        }
        if (message instanceof GreetingMessage) {
            return greetingConverter.getPayload((GreetingMessage) message);
        }
        if (message instanceof GameOverMessage) {
            return gameOverConverter.getPayload((GameOverMessage) message);
        }
        return null;
    }
}
