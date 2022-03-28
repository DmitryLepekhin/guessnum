package com.example.guessnum.message.converter;

public interface MessageSerializer<T> {
    String getPayload(T bean);
}
