package ua.univerpulse.webchat.mvc.dto;

import ua.univerpulse.webchat.mvc.domain.ChatUser;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ChatUserDto {
    @NotNull
    @Pattern(regexp = "\\w{2,}", message = "{message.name.err}")
    private String name;
    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "{message.email.err}")
    private String login;
    @NotNull
    @Size(min=5, message = "{message.password.err}")
    private String password;

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

    public static class Builder{
        ChatUserDto chatUserDto = new ChatUserDto();
        public Builder setName(ChatUser chatUser){
            chatUserDto.setName(chatUser.getName());
            return this;
        }
        public Builder setLogin(ChatUser chatUser){
            chatUserDto.setLogin(chatUser.getLogin());
            return this;
        }
        public Builder setPassword(ChatUser chatUser){
            chatUserDto.setPassword(chatUser.getPassword());
            return this;
        }
        public ChatUserDto build(){
            return chatUserDto;
        }
    }

}
