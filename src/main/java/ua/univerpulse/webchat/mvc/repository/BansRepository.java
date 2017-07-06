package ua.univerpulse.webchat.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.univerpulse.webchat.mvc.domain.Ban;

@Repository
public interface BansRepository extends JpaRepository<Ban, Long> {

    @Query("select b from Ban b where b.user.id=:userId")
    Ban findBanByChatUserId(@Param("userId") Long userId);

    @Query("select b from Ban b where b.user.login=:login")
    Ban findBanByChatUserLogin(@Param("login") String login);

}
