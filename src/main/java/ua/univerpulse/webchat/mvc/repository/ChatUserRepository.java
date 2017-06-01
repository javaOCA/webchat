package ua.univerpulse.webchat.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.univerpulse.webchat.mvc.domain.ChatUser;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser, Long>, ChatUserRepositoryCustom {

}
