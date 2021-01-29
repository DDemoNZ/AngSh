package sh.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sh.demo.models.auth.JwtResponse;
import sh.demo.models.auth.SignupRequest;
import sh.demo.models.auth.AuthenticationRequest;
import sh.demo.models.User;
import sh.demo.repository.UserJpa;
import sh.demo.security.JwtUtil;
import sh.demo.security.UserDetailsImpl;
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
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    public AccountController(UserJpa userJpa, AuthService authService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordEncoder encoder) {
        this.userJpa = userJpa;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    @PostConstruct
    public void addAdmin() {
        User admin = new User();
        admin.setFirst_name("admin");
        admin.setRole("ADMIN");
        admin.setPassword(encoder.encode("admin"));
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
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(generatedJwtToken,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getPassword(),
                roles));
    }

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
