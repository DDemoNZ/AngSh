package sh.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import sh.demo.repository.UserJpa;
import sh.demo.models.dto.User;
import sh.demo.services.AuthService;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class controller {

    private final UserJpa userJpa;
    private final ObjectMapper objectMapper;
    private final AuthService authService;

    public controller(UserJpa userJpa, ObjectMapper objectMapper, AuthService authService) {
        this.userJpa = userJpa;
        this.objectMapper = objectMapper;
        this.authService = authService;
    }

    @PostConstruct
    public void addAdmin() {
        User admin = new User();
        admin.setFirst_name("admin");
        admin.setRole("ADMIN");
        admin.setPassword("admin");
        admin.setUsername("admin");

        userJpa.save(admin);
    }

    @PostMapping("/log")
    public Object login(@RequestBody User user) {
        return authService.login(user);
    }

    @PostMapping("/reg")
    public Object register(@RequestBody User user) {
        user.setRole("USER");
        return authService.registration(user);
    }

}
