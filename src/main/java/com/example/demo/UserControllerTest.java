package com.example.demo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

public class UserControllerTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserController userController = new UserController(userRepository);
    private final WebTestClient webTestClient = WebTestClient.bindToController(userController).build();

    @Test
    public void testGetAllUsers() {
        User user1 = new User(1L, "John");
        User user2 = new User(2L, "Jane");
        when(userRepository.findAll()).thenReturn(Flux.just(user1, user2));

        webTestClient.get()
                .uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(2)
                .contains(user1, user2);
    }

    @Test
    public void testGetUser() {
        User user = new User(1L, "John");
        when(userRepository.findById(1L)).thenReturn(Mono.just(user));

        webTestClient.get()
                .uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(user);
    }

    @Test
    public void testCreateUser() {
        User user = new User(1L, "John");
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(User.class)
                .isEqualTo(user);
    }

    @Test
    public void testUpdateUser() {
        User user = new User(1L, "John");
        when(userRepository.findById(1L)).thenReturn(Mono.just(user));
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        webTestClient.put()
                .uri("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(user);
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.deleteById(1L)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }
}
