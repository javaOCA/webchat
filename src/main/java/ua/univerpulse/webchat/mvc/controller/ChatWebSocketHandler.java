package ua.univerpulse.webchat.mvc.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ua.univerpulse.webchat.mvc.domain.ChatUser;
import ua.univerpulse.webchat.mvc.listeners.HttpSessionCreationListener;
import ua.univerpulse.webchat.mvc.service.WebSocketService;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/*
        Client to Server
            {"sessionid":"smth"}
            {"broadcast":"Hello"}
            {"login":"vasya", "message":"Hi"}
            {"logout":""}
        Server to Client
            {"auth":"yes"}
            {"auth":"yes", "list":["Vasya","Petya"]}
            {"auth":"yes", "login":"Vasya", "message":"Hi"}
 */

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private Map<String, WebSocketSession> socketSessionMap = new HashMap<>();
    private Map<String, String> httpSessionLoginMap = new HashMap<>();
    private WebSocketService socketService;

    @Autowired
    public void setSocketService(WebSocketService socketService) {
        this.socketService = socketService;
    }

    @Override
    public void handleTextMessage(WebSocketSession socketSession, TextMessage message) throws Exception {
        String jsonMessage = message.getPayload();
        Gson gson = new Gson();
        Type gsonType = new TypeToken<HashMap<String, String>>() {
        }.getType();
        Map<String, String> stringMap = gson.fromJson(jsonMessage, gsonType);
        if (Objects.nonNull(stringMap.get("sessionid"))) {
            if (registration(stringMap.get("sessionid"), socketSession)) {
                socketSession.sendMessage(new TextMessage("{\"auth\":\"yes\"}"));
                sendListAllUsers();
                sendAllMessageForUser(socketSession);
            } else {
                socketSession.sendMessage(new TextMessage("{\"auth\":\"no\"}"));
            }
        } else {
            String senderLogin = getKeyByValue(socketSession);
            if (Objects.nonNull(senderLogin)) {
                if (Objects.nonNull(stringMap.get("broadcast"))) {
                    String broadcastMessage = stringMap.get("broadcast");
                    socketService.saveBroadcastMessage(broadcastMessage,senderLogin);
                    JsonObject broadcastJson = new JsonObject();
                    broadcastJson.addProperty("auth","yes");
                    broadcastJson.addProperty("name", senderLogin);
                    broadcastJson.addProperty("message",broadcastMessage);
                    sendAllActiveUsers(broadcastJson);
                } else if (Objects.nonNull(stringMap.get("login"))) {
                    String receiverLogin = stringMap.get("login");
                    String messageToForward = stringMap.get("message");
                    if (Objects.nonNull(socketSessionMap.get(receiverLogin))) {
                        forwardMessage(receiverLogin, senderLogin, messageToForward);
                    } else {
                        saveMessageToDB(receiverLogin, senderLogin, messageToForward);
                    }
                } else if (Objects.nonNull(stringMap.get("logout"))) {
                    invalidateHttpSession(socketSession);
                    removeUserFromMap(socketSession);
                } else {
                    socketSession.sendMessage(new TextMessage("bad json"));
                }
            } else {
                socketSession.sendMessage(new TextMessage("{\"auth\":\"no\"}"));
            }

        }
    }

    private boolean registration(String sessionId, WebSocketSession socketSession) {
        HttpSession httpSession = HttpSessionCreationListener.getSessionById(sessionId);
        if (Objects.nonNull(httpSession.getAttribute("user"))) {
            ChatUser chatUser = (ChatUser) httpSession.getAttribute("user");
            String login = chatUser.getLogin();
            socketSessionMap.put(login, socketSession);
            httpSessionLoginMap.put(login, sessionId);
            return true;
        }
        return false;
    }

    private void sendListAllUsers() throws IOException {
        Gson gson = new Gson();
        Set<String> activeUsersKeys = socketSessionMap.keySet();
        JsonObject listActiveUsers = new JsonObject();
        listActiveUsers.addProperty("auth", "yes");
        listActiveUsers.add("list", gson.toJsonTree(activeUsersKeys));
        for (WebSocketSession session : socketSessionMap.values()) {
            session.sendMessage(new TextMessage(listActiveUsers.toString()));
        }

    }

    private void sendAllMessageForUser(WebSocketSession socketSession) throws IOException {
        String receiverLogin = getKeyByValue(socketSession);
        List<Pair<String,String>> messages = socketService.getMessagesByLogin(receiverLogin);
        for (Pair<String,String> entry: messages) {
            sendMessage(socketSession, entry);
        }
        socketService.deletePrivateMessages(receiverLogin);
        List<Pair<String,String>> broadcastMessages = socketService.getBroadcastMessages();
        for (Pair<String,String> entry: broadcastMessages) {
            sendMessage(socketSession, entry);
        }
    }

    private void sendMessage(WebSocketSession socketSession, Pair<String, String> entry) throws IOException {
        JsonObject message = new JsonObject();
        message.addProperty("auth", "yes");
        message.addProperty("login",entry.getKey());
        message.addProperty("message",entry.getValue());
        socketSession.sendMessage(new TextMessage(message.toString()));
    }

    private String getKeyByValue(WebSocketSession socketSession) {
        for (Map.Entry<String,WebSocketSession> entry:socketSessionMap.entrySet()) {
            if(entry.getValue()==socketSession){
                return entry.getKey();
            }
        }
        return null;
    }

    private void sendAllActiveUsers(JsonObject broadcastJson) throws IOException {
        for (WebSocketSession session : socketSessionMap.values()) {
            session.sendMessage(new TextMessage(broadcastJson.toString()));
        }
    }

    private void forwardMessage(String receiverLogin, String senderLogin, String messageToForward) throws IOException {
        Gson gson = new Gson();
        JsonObject privateMessage = new JsonObject();
        privateMessage.addProperty("auth", "yes");
        privateMessage.addProperty("login",senderLogin);
        privateMessage.addProperty("message",messageToForward);
        socketSessionMap.get(receiverLogin).sendMessage(new TextMessage(privateMessage.toString()));
    }

    private void saveMessageToDB(String receiverLogin, String senderLogin, String messageToForward) {
        socketService.savePrivateMessage(receiverLogin,senderLogin,messageToForward);
    }

    private void invalidateHttpSession(WebSocketSession socketSession) {
        String login = getKeyByValue(socketSession);
        String sessionId = httpSessionLoginMap.get(login);
        httpSessionLoginMap.remove(login);
        HttpSession httpSession = HttpSessionCreationListener.getSessionById(sessionId);
        httpSession.invalidate();
    }

    private void removeUserFromMap(WebSocketSession socketSession) {
        String login = getKeyByValue(socketSession);
        socketSessionMap.remove(login);
    }

}