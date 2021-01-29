package sh.demo.models.auth;

import lombok.Data;
import sh.demo.models.User;

@Data
public class AuthResponseDto {

    private User user;
    private String exception;
}
