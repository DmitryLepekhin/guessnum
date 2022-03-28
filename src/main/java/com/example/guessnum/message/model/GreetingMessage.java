package com.example.guessnum.message.model;

import com.example.guessnum.message.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class GreetingMessage implements Message {
    @Size(min = 1, max = 32)
    @Pattern(regexp = "[\\p{IsAlphabetic}\\d'\\-\\s]+")
    private String name;
}
