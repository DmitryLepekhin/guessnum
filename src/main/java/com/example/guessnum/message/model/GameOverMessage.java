package com.example.guessnum.message.model;

import com.example.guessnum.message.Message;
import com.example.guessnum.model.Player;
import com.example.guessnum.model.Winner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class GameOverMessage implements Message {
    private int num;
    private Collection<Winner> winners;
}
