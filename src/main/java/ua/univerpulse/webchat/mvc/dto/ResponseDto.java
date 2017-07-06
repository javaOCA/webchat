package ua.univerpulse.webchat.mvc.dto;

import java.util.List;

public class ResponseDto extends AuthDto {

    private List<BanUserDto> users;

    public List<BanUserDto> getUsers() {
        return users;
    }

    public void setUsers(List<BanUserDto> users) {
        this.users = users;
    }

}
