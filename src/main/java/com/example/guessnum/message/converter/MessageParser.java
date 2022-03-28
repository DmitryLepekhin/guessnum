package com.example.guessnum.message.converter;

import com.example.guessnum.message.Message;

import java.util.Optional;
import java.util.Properties;

public interface MessageParser<T> {
    Optional<Message> parse(Properties props);
}
