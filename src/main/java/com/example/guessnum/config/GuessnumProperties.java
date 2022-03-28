package com.example.guessnum.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.guessnum", ignoreInvalidFields = true)
@Getter
@Setter
public class GuessnumProperties {
    private String wsPath;
    private Long sessionTimeout;
    private Double winFactor;
    private int period;
}
