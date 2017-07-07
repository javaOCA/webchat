package ua.univerpulse.webchat.mvc.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.univerpulse.webchat.mvc.domain.ChatUser;
import ua.univerpulse.webchat.mvc.domain.Role;
import ua.univerpulse.webchat.mvc.domain.RoleEnum;
import ua.univerpulse.webchat.mvc.dto.ChatUserDto;
import ua.univerpulse.webchat.mvc.exception.ServiceException;
import ua.univerpulse.webchat.mvc.exception.UserSaveException;
import ua.univerpulse.webchat.mvc.repository.ChatUserRepository;
import ua.univerpulse.webchat.mvc.repository.RoleRepository;
import ua.univerpulse.webchat.mvc.service.LoginService;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    private ChatUserRepository chatUserRepository;
    private RoleRepository roleRepository;

    @Autowired
    public LoginServiceImpl(ChatUserRepository chatUserRepository, RoleRepository roleRepository) {
        this.chatUserRepository = chatUserRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(ChatUserDto chatUserDto) {
        Role role = roleRepository.findRoleByRoleName(RoleEnum.USER);
        ChatUser chatUser = new ChatUser.Builder()
                .setName(chatUserDto)
                .setLogin(chatUserDto)
                .setPassword(chatUserDto)
                .setRole(role)
                .build();
        try {
            chatUserRepository.saveUser(chatUser);
        }catch (UserSaveException ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public ChatUser verifyLogin(String login, String password) {
        ChatUser user = chatUserRepository.findChatUserByLogin(login);
//        if (Objects.nonNull(user) && user.getPassword().equals(password)) return user;
        if (Objects.nonNull(user) && user.getPassword().equals(DigestUtils.md5Hex(password + login))) {
            return user;
        }
        return null;
    }

}
