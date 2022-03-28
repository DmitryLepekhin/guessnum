package com.example.guessnum.message.converter;

import com.example.guessnum.message.model.GameOverMessage;
import org.springframework.stereotype.Service;

@Service
public class GameOverConverter implements MessageSerializer<GameOverMessage> {
    @Override
    public String getPayload(GameOverMessage bean) {
        String result = "num = " + bean.getNum();
        String winnerList = bean.getWinners().stream()
                .map(winner -> winner.getName() + ": " + winner.getWin())
                .reduce((left, right) -> left + ", \\\n")
                .orElse("No one won this time");
        result += "\nwinners = " + winnerList;
        return result;
    }
}
