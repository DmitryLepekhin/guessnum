package com.example.guessnum.game;

import com.example.guessnum.config.GuessnumProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class WinCalculator {

    @Autowired
    GuessnumProperties props;

    private static final DecimalFormat format = new DecimalFormat("0.00");

    public String calculate(int bid) {
        Double factor = props.getWinFactor();
        if (factor == null) {
            factor = 1D;
        }
        return format.format(bid * factor);
    }

}
