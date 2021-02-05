package sh.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sh.demo.models.auth.AuthenticationRequest;
import sh.demo.models.auth.AuthenticationResponse;
import sh.demo.models.auth.SignupRequest;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void registerSuccess() {
        assertTrue(restTemplate.postForEntity("http://localhost:" + port + "/auth/registration",
                    new SignupRequest("username", "password", "test@test"),
                    String.class)
                .getStatusCode()
                .is2xxSuccessful());
//                .getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void login() {
        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity("http://localhost:" + port + "/auth/login",
                new AuthenticationRequest("username", "password"),
                AuthenticationResponse.class);

        assertFalse(Objects.requireNonNull(response.getBody()).getGeneratedJwtToken().isBlank());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void loginWithNonExistCredentials() {
        assertEquals(HttpStatus.UNAUTHORIZED, restTemplate.postForEntity("http://localhost:" + port + "/auth/login",
                new AuthenticationRequest("wrongUsername", "wrongPassword"),
                String.class).getStatusCode());
    }

    @Test
    void registerUsernameIsAlreadyTaken() {
        SignupRequest signupRequestWithTakenUsername = new SignupRequest("test", "test", "test@test");

        assertTrue(restTemplate.postForEntity("http://localhost:" + port + "/auth/registration",
                signupRequestWithTakenUsername,
                String.class).getStatusCode().is2xxSuccessful());

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/auth/registration",
                new SignupRequest(signupRequestWithTakenUsername.getUsername(), "test", "test@test"),
                String.class);

        assertThat(responseEntity.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }
}