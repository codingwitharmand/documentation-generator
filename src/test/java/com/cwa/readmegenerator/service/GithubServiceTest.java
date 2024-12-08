package com.cwa.readmegenerator.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class GithubServiceTest {

    @Autowired
    private GithubService githubService;

    @BeforeAll
    static void setUp() {
        var dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }

    @Test
    void shouldReturnRepositoryDetails() throws IOException {
        var repoUrl = "https://github.com/AsmrProg-YT/100-days-of-javascript.git";

        var result = githubService.getRepositoryDetails(repoUrl);

        assertNotNull(result);
        assertEquals("100-days-of-javascript", result.get("name"));
        assertEquals("master", result.get("defaultBranch"));
        assertEquals("JavaScript", result.get("language"));
    }

    @Test
    void shouldReturnRepositoryFiles() throws IOException {
        var repoUrl = "https://github.com/codingwitharmand/techstore.git";

        var result = githubService.getRepositoryFiles(repoUrl);

        assertFalse(result.isEmpty());
        assertTrue(result.size() > 5);
    }
}