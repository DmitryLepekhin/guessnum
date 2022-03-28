package com.example.guessnum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.example")
public class GuessnumServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuessnumServerApplication.class, args);
    }

}