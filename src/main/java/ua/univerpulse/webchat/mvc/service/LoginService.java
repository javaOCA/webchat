package ua.univerpulse.webchat.mvc.service;

import ua.univerpulse.webchat.mvc.domain.ChatUser;
import ua.univerpulse.webchat.mvc.dto.ChatUserDto;

public interface LoginService {

    void save(ChatUserDto chatUserDto);

    ChatUser verifyLogin(String login, String password);

}
