package sh.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sh.demo.models.dto.User;
import sh.demo.repository.UserJpa;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService {

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
}
