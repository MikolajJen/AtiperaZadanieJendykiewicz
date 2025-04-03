package com.jendykiewicz.zadanie.controller;

import com.jendykiewicz.zadanie.dto.RepositoryResponse;
import com.jendykiewicz.zadanie.service.GithubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/repositories")
public class GithubController {
    private final GithubService githubService;

    public GithubController(GithubService githubService){
        this.githubService = githubService;
    }

    @GetMapping("/{username}")
    public List<RepositoryResponse> getRepositories(@PathVariable String username){
        return githubService.getNonForkReps(username);
    }
}
