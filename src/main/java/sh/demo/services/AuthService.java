package sh.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sh.demo.models.auth.SignupRequest;
import sh.demo.models.User;
import sh.demo.repository.UserJpa;
import sh.demo.service.UserService;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder encoder;

    public AuthService(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    public User registration(SignupRequest signupRequest) {
        User user = new User();
        user.setRole("USER");
        user.setUsername(signupRequest.getUsername());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setId(userService.save(user).getId());

        return user;
    }

    public void checkIfNotExist(SignupRequest signupRequest) throws Exception {
        Optional<User> byUsername = userService.findByUsername(signupRequest.getUsername());
        if (byUsername.isPresent()) {
            throw new Exception("Username is already taken.");
        }
    }
}
