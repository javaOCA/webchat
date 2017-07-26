package ua.univerpulse.webchat.mvc.repository.impl;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.univerpulse.webchat.mvc.repository.MessageRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MessageRepositoryImpl implements MessageRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Modifying
    @Override
    @Transactional
    public void deleteByLogin(String receiverLogin) {
            em.createNativeQuery("DELETE FROM message where " +
                    "message.receiver_id in (select id from chatuser where chatuser.login=:receiverLogin)")
                    .setParameter("receiverLogin", receiverLogin).executeUpdate();
    }

}
