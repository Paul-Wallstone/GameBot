package com.vaider;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ppuchinsky
 */
@SpringBootApplication
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GameBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameBotApplication.class);
    }
}
