package ua.univerpulse.webchat.mvc.service;

import javafx.util.Pair;
import java.util.List;

public interface WebSocketService {

    void saveBroadcastMessage(String broadcastMessage, String senderLogin);
    List<Pair<String,String>> getMessagesByLogin(String receiverLogin);
    List<Pair<String,String>> getBroadcastMessages();
    void savePrivateMessage(String receiverLogin, String senderLogin, String messageToForward);
    void deletePrivateMessages(String receiverLogin);

}
