package sh.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String first_name;
    private String role;

    public User(String username, String password, String first_name, String role) {
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.role = role;
    }
}
