package sh.demo.models.auth;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {

    private final Long id;
    private final String username;
    private final String password;
    private final List<String> roles;
    private final String generatedJwtToken;

    public JwtResponse(String generatedJwtToken, Long id, String username, String password, List<String> roles) {
        this.generatedJwtToken = generatedJwtToken;
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
