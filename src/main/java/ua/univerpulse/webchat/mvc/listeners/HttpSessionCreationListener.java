package ua.univerpulse.webchat.mvc.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

@WebListener
public class HttpSessionCreationListener implements HttpSessionListener {

    private static Map<String, HttpSession> httpSessionMap = new HashMap<>();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession httpSession = httpSessionEvent.getSession();
        String sessionId = httpSession.getId();
        httpSessionMap.put(sessionId, httpSession);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession httpSession = httpSessionEvent.getSession();
        String sessionId = httpSession.getId();
        httpSessionMap.remove(sessionId);
    }

    public static HttpSession getSessionById(String sessionId){
        return httpSessionMap.get(sessionId);
    }

}
