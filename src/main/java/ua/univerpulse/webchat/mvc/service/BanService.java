package ua.univerpulse.webchat.mvc.service;

import ua.univerpulse.webchat.mvc.dto.BanUserDto;
import ua.univerpulse.webchat.mvc.dto.ChatUserDto;
import java.util.List;

public interface BanService {
    
    void addUserToBanList(ChatUserDto chatUserDto);

    void deleteUserFromBanList(Long id);

    List<ChatUserDto> getAllUsersExceptAdmin();

    boolean isUserBaned(BanUserDto banUserDto);

}
