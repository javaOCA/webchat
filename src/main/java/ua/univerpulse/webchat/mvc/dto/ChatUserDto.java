package ua.univerpulse.webchat.mvc.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ChatUserDto {
    @NotNull
    @Pattern(regexp = "\\w{2,}", message = "{message.name.err}")
    private String name;
    @NotNull
    @Email(message = "{message.email.err}")
    private String login;
    @NotNull
    @Size(min=5, message = "{message.password.err")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
