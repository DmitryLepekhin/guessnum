package com.example.guessnum.message.converter;

import com.example.guessnum.message.Message;
import com.example.guessnum.message.MessageValidator;
import com.example.guessnum.message.model.GreetingMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Properties;

@Service
public class GreetingConverter implements MessageParser<GreetingMessage>, MessageSerializer<GreetingMessage> {

    @Autowired
    private MessageValidator validator;

    @Override
    public Optional<Message> parse(Properties props) {
        String name = props.getProperty("name");
        if (name == null) {
            return Optional.empty();
        }
        GreetingMessage message = new GreetingMessage(name);
        return validator.validateOrEmptyOptional(message);
    }

    @Override
    public String getPayload(GreetingMessage bean) {
        return "name = " + bean.getName();
    }
}
