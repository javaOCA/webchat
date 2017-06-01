package ua.univerpulse.webchat.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.univerpulse.webchat.mvc.domain.ChatUser;
import ua.univerpulse.webchat.mvc.dto.ChatUserDto;
import ua.univerpulse.webchat.mvc.exception.ServiceException;
import ua.univerpulse.webchat.mvc.exception.UserSaveException;
import ua.univerpulse.webchat.mvc.repository.ChatUserRepository;
import ua.univerpulse.webchat.mvc.service.LoginService;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private ChatUserRepository chatUserRepository;

    @Autowired
    public LoginServiceImpl(ChatUserRepository chatUserRepository) {
        this.chatUserRepository = chatUserRepository;
    }

    @Override
    public void save(ChatUserDto userDto) {
        ChatUser chatUser = new ChatUser.Builder()
                .setName(userDto).setLogin(userDto).setPassword(userDto).build();
        try {
            chatUserRepository.saveUser(chatUser);
        } catch (UserSaveException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

}
