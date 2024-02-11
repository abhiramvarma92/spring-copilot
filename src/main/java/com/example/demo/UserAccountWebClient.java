package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class UserAccountWebClient {

    private WebClient webClient;

    public UserAccountWebClient(String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<UserAccount> getUserAccount(String id) {
        return webClient.get()
                .uri("/accounts/{id}", id)
                .retrieve()
                .bodyToMono(UserAccount.class);
    }

    public Mono<UserAccount> createUserAccount(UserAccount userAccount) {
        return webClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userAccount)
                .retrieve()
                .bodyToMono(UserAccount.class);
    }

    public Mono<UserAccount> updateUserAccount(String id, UserAccount userAccount) {
        return webClient.put()
                .uri("/accounts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userAccount)
                .retrieve()
                .bodyToMono(UserAccount.class);
    }
}
