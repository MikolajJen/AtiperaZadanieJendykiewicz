package com.jendykiewicz.zadanie.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class GithubClient {

    //klient HTTP
    private final RestTemplate restTemplate = new RestTemplate();

    //pobieramy liste repozytoriów
    @GetMapping("/{username}")
    public List<GithubRepo> getRepositories(String username){
        String url = "https://api.github.com/users/" + username + "/repos";

        try{
            GithubRepo[] repos = restTemplate.getForObject(url, GithubRepo[].class);

            return List.of(repos);
        }
        catch (HttpClientErrorException.NotFound e){
            throw new GithubUserNotFoundException("User not found");
        }
        catch (HttpMessageNotReadableException e) {
            throw new GithubUserNotFoundException("User not found");
        }
        catch (RestClientException e){
            throw new GithubUserNotFoundException("User not found");
        }
    }

    //Pobieramy info o branchu
    public List<GithubBranch> getBranches(String owner, String repo){
        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/branches";
        GithubBranch[] branches = restTemplate.getForObject(url, GithubBranch[].class);
        return List.of(branches);
    }


    //Wszystko poniżej mapuje odpowiedzi JSON z Githuba

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record GithubRepo(String name, GithubOwner owner, boolean fork) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record GithubOwner(String login) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record GithubBranch(String name, Commit commit) {
        public record Commit(String sha) {}
    }

    //customowy wyjątek o nie znalezieniu użytkownika na githubie
    public static class GithubUserNotFoundException extends RuntimeException {
        public GithubUserNotFoundException(String message){
            super(message);
        }
    }
}


