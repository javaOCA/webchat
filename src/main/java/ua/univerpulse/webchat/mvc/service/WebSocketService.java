package ua.univerpulse.webchat.mvc.service;

import java.util.Map;

public interface WebSocketService {

    void saveBroadcastMessage(String broadcastMessage, String senderLogin);
    Map<String,String> getMessagesByLogin(String receiverLogin);
    Map<String,String> getBroadcastMessages();
    void savePrivateMessage(String receiverLogin, String senderLogin, String messageToForward);

}
