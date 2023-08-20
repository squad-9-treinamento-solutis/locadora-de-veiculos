package br.com.solutis.locadora;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class LocadoraApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocadoraApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Locadora!");
        SpringApplication.run(LocadoraApplication.class, args);
        LOGGER.info("Locadora started successfully!");
    }
}
