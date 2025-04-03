package com.jendykiewicz.zadanie.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GithubControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnRepsForExistingUser() throws Exception {
        mockMvc.perform(get("/repositories/MikolajJen")) // Moje repozytoria na potrzeby testów integracyjnych
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldReturn404ForNonExistentUser() throws Exception {
        mockMvc.perform(get("/repositories/NieIstniejacyUser123456789"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void shouldReturnRepositoryWithExpectedFields() throws Exception{
        mockMvc.perform(get("/repositories/MikolajJen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].repositoryName").exists())
                .andExpect(jsonPath("$[0].ownerLogin").exists())
                .andExpect(jsonPath("$[0].branches").exists())
                .andExpect(jsonPath("$[0].branches[0].name").exists())
                .andExpect(jsonPath("$[0].branches[0].lastCommitSha").exists());

    }

    @Test
    void shouldReturnEmptyListWhenUserHasNoRepositories() throws Exception{
        mockMvc.perform(get("/repositories/ghost"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturn404ForInvalidUsernames() throws Exception{
    //Test zbiorczy dla nietypowych przypadków
        String[] invalidUsernameList = {
                "inv@l!dus3r", //znaki specjalne
                "user  invalid", //spacja w środku
                "invalid-", //złe zakończenie
                "😂😂🤣", //emoji
        };

        for(String username : invalidUsernameList) {
            mockMvc.perform(get("/repositories/" + username))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.message").value("User not found"));
        }
    }

    @Test
    void shouldExpect400IfUsernameIsMissing() throws Exception {
        mockMvc.perform(get("/repositories/"))
                .andExpect(status().isNotFound());


    }

    @Test
    void shouldExpect400IfUsernameIsBlank() throws Exception {
        mockMvc.perform(get("/repositories/ "))
                .andExpect(status().isNotFound());
    }

}
