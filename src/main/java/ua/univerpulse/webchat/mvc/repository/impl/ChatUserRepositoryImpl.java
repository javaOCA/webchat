package ua.univerpulse.webchat.mvc.repository.impl;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.univerpulse.webchat.mvc.domain.ChatUser;
import ua.univerpulse.webchat.mvc.exception.UserSaveException;
import ua.univerpulse.webchat.mvc.repository.ChatUserRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

public class ChatUserRepositoryImpl implements ChatUserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(ChatUser user) {
        try {
            em.persist(user);
            em.flush();
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                throw new UserSaveException(messageSource.getMessage("user.exist", null, LocaleContextHolder.getLocale()));
            } else {
                throw new UserSaveException(messageSource.getMessage("db.error", null, LocaleContextHolder.getLocale()));
            }
        }
    }
}
