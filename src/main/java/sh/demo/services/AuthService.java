package sh.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sh.demo.models.SignupRequest;
import sh.demo.models.dto.AuthResponseDto;
import sh.demo.models.dto.User;
import sh.demo.repository.UserJpa;

import java.util.Optional;

@Service
public class AuthService {

    private final UserJpa userJpa;
    private final PasswordEncoder encoder;

    public AuthService(UserJpa userJpa, PasswordEncoder encoder) {
        this.userJpa = userJpa;
        this.encoder = encoder;
    }

    public User registration(SignupRequest signupRequest) {
        User user = new User();
        user.setRole("USER");
        user.setUsername(signupRequest.getUsername());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setId(userJpa.save(user).getId());

        return user;
    }

    public void checkIfNotExist(SignupRequest signupRequest) throws Exception {
        Optional<User> byUsername = userJpa.findByUsername(signupRequest.getUsername());
        if (byUsername.isPresent()) {
            throw new Exception("Error: Username is already taken.");
        }
    }
}
