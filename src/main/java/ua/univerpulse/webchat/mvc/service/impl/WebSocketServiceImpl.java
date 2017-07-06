package ua.univerpulse.webchat.mvc.service.impl;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.univerpulse.webchat.mvc.domain.ChatUser;
import ua.univerpulse.webchat.mvc.domain.Message;
import ua.univerpulse.webchat.mvc.repository.ChatUserRepository;
import ua.univerpulse.webchat.mvc.repository.MessageRepository;
import ua.univerpulse.webchat.mvc.service.WebSocketService;
import ua.univerpulse.webchat.mvc.service.redis.RedisDao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    private MessageRepository messageRepository;
    private ChatUserRepository chatUserRepository;
    private RedisDao redisDao;

    @Autowired
    public WebSocketServiceImpl(MessageRepository messageRepository, ChatUserRepository chatUserRepository, RedisDao redisDao) {
        this.messageRepository = messageRepository;
        this.chatUserRepository = chatUserRepository;
        this.redisDao = redisDao;
    }

    @Override
    public void saveBroadcastMessage(String broadcastMessage, String senderLogin) {
        String value = senderLogin + ":" + broadcastMessage;
        redisDao.saveDataByKey("broadcast", value);
    }

    @Override
    @Transactional
    public List<Pair<String,String>> getMessagesByLogin(String receiverLogin) {
        List<Message> messages = messageRepository.findMessagesByLogin(receiverLogin);
        List<Pair<String,String>> mapMessages = new ArrayList<>();
        for (Message message: messages){
            System.out.println(message.getSender());
            mapMessages.add(
                    new Pair<String, String>(message.getSender().getLogin(),message.getBody()));
        }
        return mapMessages;
    }

    public void deletePrivateMessages(String receiverLogin){
        messageRepository.deleteByLogin(receiverLogin);
    }

    @Override
    public List<Pair<String,String>> getBroadcastMessages() {
        List<String> messages = redisDao.getAllDataByKey("broadcast");
        List<Pair<String,String>> mapMessages = new ArrayList<>();
        for (String str : messages) {
            String[] senderAndMessage = str.split(":");
            mapMessages.add(new Pair<String, String>(senderAndMessage[0], senderAndMessage[1]));
        }
        return mapMessages;
    }

    @Override
    @Transactional
    public void savePrivateMessage(String receiverLogin, String senderLogin, String messageToForward) {
        Message message = new Message();
        message.setBody(messageToForward);
        ChatUser sender = chatUserRepository.findChatUserByLogin(senderLogin);
        ChatUser receiver = chatUserRepository.findChatUserByLogin(receiverLogin);
        message.setSender(sender);
        message.setDate(LocalDateTime.now());
        message.setReceiver(receiver);
        messageRepository.save(message);
    }
}
