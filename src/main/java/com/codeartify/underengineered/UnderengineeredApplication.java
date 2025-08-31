package com.codeartify.underengineered;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class UnderengineeredApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(UnderengineeredApplication.class);
        app.setAdditionalProfiles("underengineered");
        app.run(args);
    }
}
