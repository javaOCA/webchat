package ua.univerpulse.webchat.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.univerpulse.webchat.mvc.domain.ChatUser;

import java.util.List;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser, Long>, ChatUserRepositoryCustom {

    public ChatUser findChatUserByLogin(String login);

    @Query("select u from ChatUser u where u.role.role<>ua.univerpulse.webchat.mvc.domain.RoleEnum.ADMIN")
    List<ChatUser> findAllExceptAdmin();

}
