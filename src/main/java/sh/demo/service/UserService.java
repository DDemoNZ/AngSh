package sh.demo.service;

import sh.demo.models.User;

import java.util.Optional;

public interface UserService {

   User save(User user);

    Optional<User> findByUsername(String username);

}
