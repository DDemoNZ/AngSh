package sh.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sh.demo.models.User;
import sh.demo.repository.UserJpa;
import sh.demo.security.UserDetailsImpl;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserJpa userJpa;

    public UserServiceImpl(UserJpa userJpa) {
        this.userJpa = userJpa;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJpa.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return UserDetailsImpl.build(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpa.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userJpa.save(user);
    }
}
