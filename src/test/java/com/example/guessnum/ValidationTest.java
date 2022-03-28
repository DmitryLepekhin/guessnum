package com.example.guessnum;

import com.example.guessnum.message.MessageValidator;
import com.example.guessnum.message.model.BidMessage;
import com.example.guessnum.message.model.GreetingMessage;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationTest {

    @Test
    public void testBidValidation() {
        MessageValidator validator = new MessageValidator();
        Set<ConstraintViolation<BidMessage>> violations;

        // valid message
        violations = validator.validate(new BidMessage(5, 1001));
        assertEquals(0, violations.size());

        // bid must be positive
        violations = validator.validate(new BidMessage(5, 0));
        assertEquals(1, violations.size());

        // num must be greater 0
        violations = validator.validate(new BidMessage(0, 11));
        assertEquals(1, violations.size());

        // num must be less then or equal to 10
        violations = validator.validate(new BidMessage(11, 11));
        assertEquals(1, violations.size());
    }

    @Test
    public void testGreetingValidation() {
        MessageValidator validator = new MessageValidator();
        Set<ConstraintViolation<GreetingMessage>> violations;

        // valid message
        violations = validator.validate(new GreetingMessage("Charles D'Artagnan 3"));
        assertEquals(0, violations.size());

        // too short and doesn't match to the pattern
        violations = validator.validate(new GreetingMessage(""));
        assertEquals(2, violations.size());

        // too long
        violations = validator.validate(new GreetingMessage("123456789012345678901234567890012"));
        assertEquals(1, violations.size());

        // wrong symbols
        violations = validator.validate(new GreetingMessage("<a href"));
        assertEquals(1, violations.size());

    }
}
