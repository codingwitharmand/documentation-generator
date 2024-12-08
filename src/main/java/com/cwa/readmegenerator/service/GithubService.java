package com.cwa.readmegenerator.service;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GithubService {

    @Value("${github.api.token}")
    private String githubApiToken;

    public Map<String, Object> fetchRepositoryDetails(String repositoryUrl) throws IOException {
        var repository = getGithubRepository(repositoryUrl);

        var details = new HashMap<String, Object>();
        details.put("name", repository.getName());
        details.put("description", repository.getDescription());
        details.put("language", repository.getLanguage());
        details.put("stars", repository.getStargazersCount());
        details.put("forks", repository.getForksCount());
        details.put("defaultBranch", repository.getDefaultBranch());

        return details;
    }

    private GHRepository getGithubRepository(String repositoryUrl) throws IOException {
        final var repositoryName = getRepositoryName(repositoryUrl);

        var gitHub = new GitHubBuilder().withOAuthToken(githubApiToken).build();

        return gitHub.getRepository(repositoryName);
    }

    /**
     *
     * @param repositoryUrl the url of the repo
     * @return owner/repoName
     */
    private String getRepositoryName(String repositoryUrl) {
        return repositoryUrl.replace("https://github.com/", "")
                .replace("http://github.com/", "")
                .replace(".git", "")
                .trim();
    }

    public List<String> getRepositoryFiles(String repositoryUrl) throws IOException {
        var repository = getGithubRepository(repositoryUrl);

        var files = new ArrayList<String>();
        repository.getTreeRecursive(repository.getDefaultBranch(), 1)
                .getTree()
                .forEach(entry -> {
                    if ("blob".equals(entry.getType())) {
                        files.add(entry.getPath());
                    }
                });

        return files;
    }

}
