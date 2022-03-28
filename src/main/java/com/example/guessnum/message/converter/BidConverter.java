package com.example.guessnum.message.converter;

import com.example.guessnum.message.Message;
import com.example.guessnum.message.MessageValidator;
import com.example.guessnum.message.model.BidMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Properties;

@Service
public class BidConverter implements MessageParser<BidMessage>, MessageSerializer<BidMessage> {

    @Autowired
    private MessageValidator validator;

    @Override
    public Optional<Message> parse(Properties props) {
        String numString = props.getProperty("num");
        String bidString = props.getProperty("bid");
        if (numString == null || bidString == null) {
            return Optional.empty();
        }
        try {
            int num = Integer.parseInt(numString);
            int bid = Integer.parseInt(bidString);
            BidMessage message = new BidMessage(num, bid);
            return validator.validateOrEmptyOptional(message);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public String getPayload(BidMessage bean) {
        return "num = " + bean.getNum() + "\n" +
               "bid = " + bean.getBid();
    }
}
