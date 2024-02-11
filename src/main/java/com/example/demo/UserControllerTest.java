package com.example.demo;

import com.example.demo.User;
import com.example.demo.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;
import org.mockito.Mock;

public class UserControllerTest {

    // ...

    @Mock
    private UserRepository userRepository;

    // ...

    @Test
    public void testGetAllUsers() {
        User user1 = new User("1", "John Doe", "john@example.com");
        User user2 = new User("2", "Jane Smith", "jane@example.com");

        when(userRepository.findAll()).thenReturn(Flux.just(user1, user2));

        webTestClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(2)
                .contains(user1, user2);
    }

    @Test
    public void testGetUser() {
        User user = new User("1", "John Doe", "john@example.com");

        when(userRepository.findById("1")).thenReturn(Mono.just(user));

        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(user);
    }

    @Test
    public void testGetUser_NotFound() {
        when(userRepository.findById("1")).thenReturn(Mono.empty());

        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testCreateUser() {
        User user = new User("1", "John Doe", "john@example.com");

        when(userRepository.save(user)).thenReturn(Mono.just(user));

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(user);
    }

    @Test
    public void testUpdateUser() {
        User existingUser = new User("1", "John Doe", "john@example.com");
        User updatedUser = new User("1", "John Smith", "john@example.com");

        when(userRepository.findById("1")).thenReturn(Mono.just(existingUser));
        when(userRepository.save(existingUser)).thenReturn(Mono.just(updatedUser));

        webTestClient.put().uri("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedUser)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(updatedUser);
    }

    @Test
    public void testUpdateUser_NotFound() {
        User user = new User("1", "John Doe", "john@example.com");

        when(userRepository.findById("1")).thenReturn(Mono.empty());

        webTestClient.put().uri("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testDeleteUser() {
        User user = new User("1", "John Doe", "john@example.com");

        when(userRepository.findById("1")).thenReturn(Mono.just(user));
        when(userRepository.delete(user)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/users/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testDeleteUser_NotFound() {
        when(userRepository.findById("1")).thenReturn(Mono.empty());

        webTestClient.delete().uri("/users/1")
                .exchange()
                .expectStatus().isNotFound();
    }
}