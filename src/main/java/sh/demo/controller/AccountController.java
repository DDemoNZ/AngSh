package sh.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sh.demo.models.JwtResponse;
import sh.demo.models.SignupRequest;
import sh.demo.models.dto.AuthenticationRequest;
import sh.demo.repository.UserJpa;
import sh.demo.models.dto.User;
import sh.demo.security.JwtUtil;
import sh.demo.service.UserDetailsImpl;
import sh.demo.services.AuthService;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

    private final UserJpa userJpa;
    private final ObjectMapper objectMapper;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AccountController(UserJpa userJpa, ObjectMapper objectMapper, AuthService authService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userJpa = userJpa;
        this.objectMapper = objectMapper;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
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

    @PostMapping("/login")
    public Object login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String generatedJwtToken = jwtUtil.generateJwtToken(authenticate);

        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                                                         .map(role -> role.getAuthority())
                                                        .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(generatedJwtToken,
                                                 userDetails.getId(),
                                                 userDetails.getUsername(),
                                                 userDetails.getPassword(),
                                                 roles));
    }
//
//    @GetMapping("/")
//    public String test(@Auth)

    @PostMapping("/registration")
    public Object register(@RequestBody @Valid SignupRequest signupRequest) {
        try {
            authService.checkIfNotExist(signupRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        User user = authService.registration(signupRequest);
        return ResponseEntity.ok().body("User registered successfully.");
    }

}
