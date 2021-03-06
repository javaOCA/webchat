package ua.univerpulse.webchat.mvc.domain;

import org.apache.commons.codec.digest.DigestUtils;
import ua.univerpulse.webchat.mvc.dto.ChatUserDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="chatuser")
public class ChatUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String login;
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Message> sendMessages;
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Message> receiveMessages;

    public List<Message> getSendMessages() {
        return sendMessages;
    }

    public void setSendMessages(List<Message> sendMessages) {
        this.sendMessages = sendMessages;
    }

    public List<Message> getReceiveMessages() {
        return receiveMessages;
    }

    public void setReceiveMessages(List<Message> receiveMessages) {
        this.receiveMessages = receiveMessages;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static class Builder{
        ChatUser chatUser = new ChatUser();
        public Builder setName(ChatUserDto userDto){
            chatUser.setName(userDto.getName());
            return this;
        }
        public Builder setLogin(ChatUserDto userDto){
            chatUser.setLogin(userDto.getLogin());
            return this;
        }
        public Builder setPassword(ChatUserDto userDto){
//            chatUser.setPassword(userDto.getPassword());
            chatUser.setPassword(DigestUtils.md5Hex(userDto.getPassword() + userDto.getLogin()));
            return this;
        }
        public Builder setRole(Role role) {
            chatUser.setRole(role);
            return this;
        }
        public ChatUser build(){
            return chatUser;
        }
    }
}
