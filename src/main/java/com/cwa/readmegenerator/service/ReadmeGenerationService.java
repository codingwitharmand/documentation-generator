package com.cwa.readmegenerator.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ReadmeGenerationService {

    private final ChatClient chatClient;

    public ReadmeGenerationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generateReadmeContent(Map<String, Object> repositoryDetails, List<String> files) {
        var prompt = buildPrompt(repositoryDetails, files);

        Map<String, String> completion = Map.of(
                "completion", Objects.requireNonNull(chatClient.prompt().user(prompt).call().content())
        );

        return completion.get("completion");
    }

    private String buildPrompt(Map<String, Object> repositoryDetails, List<String> files) {
        return String.format(
                """
                    Generate a professional README.md for a GitHub repository with the following details:
                    Repository Name: %s
                    Description: %s
                    Primary Language: %s
                    Files in the repository: %s
                    Please create a comprehensive README that includes:
                    1. A clear project overview
                    2. Key features
                    3. Installation instructions
                    4. Usage examples
                    5. Any additional relevant sections
                    Format the output in GitHub Markdown.
                """,
                repositoryDetails.get("name"),
                repositoryDetails.get("description"),
                repositoryDetails.get("language"),
                String.join(", ", files)
        );
    }
}
