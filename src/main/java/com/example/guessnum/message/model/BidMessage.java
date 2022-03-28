package com.example.guessnum.message.model;

import com.example.guessnum.message.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
public class BidMessage implements Message {
    @Min(1)
    @Max(10)
    private int num;
    @Positive
    private int bid;
}
