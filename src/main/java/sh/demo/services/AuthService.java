package sh.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sh.demo.models.dto.User;
import sh.demo.repository.UserJpa;
import sh.demo.models.dto.AuthResponseDto;

import java.util.Optional;

@Service
public class AuthService {

    private final UserJpa userJpa;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserJpa userJpa, PasswordEncoder passwordEncoder) {
        this.userJpa = userJpa;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDto login(User user) {
        Optional<User> byUsername = userJpa.findByUsername(user.getUsername());
        AuthResponseDto authResponseDto = new AuthResponseDto();
        if (byUsername.isPresent()
                && byUsername.get().getUsername().equals(user.getUsername())
                && byUsername.get().getPassword().equals(/*passwordEncoder.encode(*/user.getPassword()))/*)*/ {
            authResponseDto.setUser(byUsername.get());
        } else {
            authResponseDto.setException("No such user");
        }
        return authResponseDto;
    }

    public AuthResponseDto registration(User user) {
        AuthResponseDto authResponseDto = new AuthResponseDto();
        if (userJpa.findByUsername(user.getUsername()).isPresent()) {
            authResponseDto.setException("Username is already in use.");
        } else {
            User save = userJpa.save(user);
            authResponseDto.setUser(save);
        }
        return authResponseDto;
    }
}
