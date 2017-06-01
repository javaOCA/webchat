package ua.univerpulse.webchat.mvc.repository.impl;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import ua.univerpulse.webchat.mvc.domain.ChatUser;
import ua.univerpulse.webchat.mvc.exception.UserSaveException;
import ua.univerpulse.webchat.mvc.repository.ChatUserRepository;
import ua.univerpulse.webchat.mvc.repository.ChatUserRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

public class ChatUserRepositoryImpl implements ChatUserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveUser(ChatUser chatUser) {
        try {
            em.persist(chatUser);
        } catch (JpaSystemException ex) {
            if (ex.getCause() instanceof PersistenceException) {
                PersistenceException persistenceException = (PersistenceException) ex.getCause();
                if (persistenceException.getCause() instanceof ConstraintViolationException) {
                     throw new UserSaveException("user.exist");
                }
            }
            throw new UserSaveException("db.error");
        }
    }

}
