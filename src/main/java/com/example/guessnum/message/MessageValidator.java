package com.example.guessnum.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

@Service
public class MessageValidator {

    Logger logger = LoggerFactory.getLogger(MessageValidator.class);

    private Validator validator;

    public MessageValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public <T> Optional<T> validateOrEmptyOptional(T bean) {
        Set<ConstraintViolation<T>> violations = validate(bean);
        if (violations.isEmpty()) {
            return Optional.of(bean);
        } else {
            violations.stream().map(ConstraintViolation::getMessage).forEach(logger::error);
            return Optional.empty();
        }
    }

    public <T> Set<ConstraintViolation<T>> validate(T bean) {
        return validator.validate(bean);
    }

}
