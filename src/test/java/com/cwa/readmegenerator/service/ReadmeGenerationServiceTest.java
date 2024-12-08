package com.cwa.readmegenerator.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ReadmeGenerationServiceTest {

    @Autowired
    private ReadmeGenerationService readmeGenerationService;

    @BeforeAll
    static void setUp() {
        var dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }

    @Test
    void shouldGenerateReadme() {
        Map<String, Object> details = Map.of(
          "name", "codingwitharmand",
          "description", "Tech Store - Spring Boot Web App",
          "language", "Java"
        );

        var files = List.of(".gitignore", "README.md", "Dockerfile", "docker-compose.yml");

        var result = readmeGenerationService.generateReadmeContent(details, files);

        assertNotNull(result);
    }

}