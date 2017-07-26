package ua.univerpulse.webchat.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.univerpulse.webchat.mvc.domain.Message;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {

    @Query("select m from Message m where m.receiver.login=:receiverLogin")
    List<Message> findMessagesByLogin(@Param("receiverLogin") String receiverLogin);

}
