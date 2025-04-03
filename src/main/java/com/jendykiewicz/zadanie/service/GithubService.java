package com.jendykiewicz.zadanie.service;

import com.jendykiewicz.zadanie.client.GithubClient;
import com.jendykiewicz.zadanie.dto.BranchResponse;
import com.jendykiewicz.zadanie.dto.RepositoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GithubService {
    private final GithubClient githubClient;

    public GithubService(GithubClient githubClient){
        this.githubClient = githubClient;
    }

    public List<RepositoryResponse> getNonForkReps(String username){
        List <GithubClient.GithubRepo> allRepos = githubClient.getRepositories(username);

        return allRepos.stream()
                .filter(repo -> !repo.fork()) // odrzucamy forki
                .map(repo -> {
                    List<GithubClient.GithubBranch> branches = githubClient.getBranches(repo.owner().login(), repo.name()); //pobieramy informacje o branchach z Githuba

                    List<BranchResponse> branchResponses = branches.stream()
                            .map(branch -> new BranchResponse(branch.name(), branch.commit().sha()))
                            .toList();
                    return new RepositoryResponse(
                            repo.name(),
                            repo.owner().login(),
                            branchResponses
                    );
                })
                .toList(); //zwracamy liste do kontrolera
    }
}
