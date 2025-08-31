package com.codeartify.overengineered;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class OverengineeredApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OverengineeredApplication.class);
        app.setAdditionalProfiles("overengineered");
        app.run(args);
    }
}
