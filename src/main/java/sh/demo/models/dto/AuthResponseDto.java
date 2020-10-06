package sh.demo.models.dto;

import lombok.Data;

@Data
public class AuthResponseDto {

    private User user;
    private String exception;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
