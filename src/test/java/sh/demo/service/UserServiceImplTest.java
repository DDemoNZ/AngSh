package sh.demo.service;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import sh.demo.models.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration-test.properties")
class UserServiceImplTest {

    public static final String wrongUsername = "wrongUsername";
    public static final String expectedUsername = "expectedUsername";

    @Autowired
    private UserService userService;

    @Test
    public void save() {
        User user = new User("expectedUsername", "test", "test", "test");
        User save = userService.save(user);
        Optional<User> storedUser = userService.findByUsername(user.getUsername());

        assertTrue(storedUser.isPresent());
        assertEquals(2L, save.getId());
    }

    @Test
    public void saveUserWithNonUniqueUsername() {
        User user = new User("testUnique", "test", "test", "test");
        User nonUniqueUser = new User("testUnique", "test", "test", "test");

        User save = userService.save(user);
        Optional<User> storedUser = userService.findByUsername(user.getUsername());

        assertThrows(DataIntegrityViolationException.class,() -> userService.save(nonUniqueUser));
        assertTrue(storedUser.isPresent());
        assertEquals(3L, save.getId());
    }

    @Test
    public void loadUserByUsernameTest() {
        Optional<User> storedUser = userService.findByUsername(expectedUsername);

        assertTrue(storedUser.isPresent());
        assertEquals(expectedUsername, storedUser.get().getUsername());
    }

    @Test
    public void loadUserByWrongUsernameTest() {
        Optional<User> storedUser = userService.findByUsername(wrongUsername);

        assertTrue(storedUser.isEmpty());
    }

    @Test
    public void findByUsername() {
        Optional<User> storedUSer = userService.findByUsername(expectedUsername);

        assertTrue(storedUSer.isPresent());
        assertEquals(expectedUsername, storedUSer.get().getUsername());
    }

    @Test
    public void findByWrongUsername() {
        Optional<User> storedUSer = userService.findByUsername(wrongUsername);

        assertTrue(storedUSer.isEmpty());
    }
}
