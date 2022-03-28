package com.example.guessnum.message;

import java.util.Optional;

public interface MessageConverter {

    /**
     * Parses a payload received from a player
     * and returns a parsed Message if the payload data is valid
     *
     * @param payload data received from a player
     * @return message if the payload is valid, an empty optional otherwise
     */
    Optional<Message> parse(String payload);

    /**
     * Converts the message to string
     *
     */
    String serialize(Message message);

}
