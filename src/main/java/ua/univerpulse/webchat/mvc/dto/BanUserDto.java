package ua.univerpulse.webchat.mvc.dto;

import org.springframework.hateoas.ResourceSupport;

public class BanUserDto extends ResourceSupport {

    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
