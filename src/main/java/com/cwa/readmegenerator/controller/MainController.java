package com.cwa.readmegenerator.controller;

import com.cwa.readmegenerator.service.GithubService;
import com.cwa.readmegenerator.service.ReadmeGenerationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/readme")
public class MainController {

    private final GithubService githubService;
    private final ReadmeGenerationService readmeGenerationService;

    public MainController(GithubService githubService, ReadmeGenerationService readmeGenerationService) {
        this.githubService = githubService;
        this.readmeGenerationService = readmeGenerationService;
    }

    @GetMapping
    public String getReadme(@RequestParam String repositoryUrl) throws IOException {
        var repoDetails = githubService.getRepositoryDetails(repositoryUrl);
        var files = githubService.getRepositoryFiles(repositoryUrl);

        return readmeGenerationService.generateReadmeContent(repoDetails, files);
    }
}
