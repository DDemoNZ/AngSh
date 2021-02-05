package sh.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sh.demo.models.Item;
import sh.demo.models.auth.AuthenticationRequest;
import sh.demo.models.auth.AuthenticationResponse;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemControllerTest {

    private String authorizationHeader;

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveItem() {
        Item item = new Item("test", 100L, "test");

        assertTrue(restTemplate.postForEntity("/item",
                buildRequestEntityWithAuthorizationHeader(item),
                String.class)
                .getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    void saveNonUniqueItem() {
        Item item = new Item("testUnique", 100L, "test");
        Item item1 = new Item("testUnique", 100L, "test");

        assertTrue(restTemplate.postForEntity("/item",
                buildRequestEntityWithAuthorizationHeader(item),
                String.class)
                .getStatusCode()
                .is2xxSuccessful());

        assertTrue(restTemplate.postForEntity("/item",
                buildRequestEntityWithAuthorizationHeader(item1),
                String.class)
                .getStatusCode()
                .is5xxServerError());
    }

    @Test
    void getAllItems() {

        HttpStatus statusCode = restTemplate.exchange(String.format("/item?page=%s&size=%s", "1", "5"), HttpMethod.GET, buildRequestWithAuthorizationHeader(), String.class).getStatusCode();
        System.err.println(statusCode);

    }

    @Test
    void getAllItemsSortedBy() {
    }

    private <T> HttpEntity buildRequestEntityWithAuthorizationHeader(T object) {
        ResponseEntity<AuthenticationResponse> responseEntity = restTemplate.postForEntity("/auth/login", new AuthenticationRequest("admin", "admin"), AuthenticationResponse.class);
        authorizationHeader = responseEntity.getBody().getGeneratedJwtToken();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authorizationHeader);
        HttpEntity requestEntity = new HttpEntity(object, headers);
        System.err.println(requestEntity);
        return requestEntity;
    }
    private <T> HttpEntity<?> buildRequestWithAuthorizationHeader() {
        ResponseEntity<AuthenticationResponse> responseEntity = restTemplate.postForEntity("/auth/login", new AuthenticationRequest("admin", "admin"), AuthenticationResponse.class);
        authorizationHeader = responseEntity.getBody().getGeneratedJwtToken();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authorizationHeader);
        return new HttpEntity<>(headers);
    }
}