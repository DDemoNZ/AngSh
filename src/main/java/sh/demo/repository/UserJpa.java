package sh.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sh.demo.models.User;

import java.util.Optional;

@Repository
public interface UserJpa extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
