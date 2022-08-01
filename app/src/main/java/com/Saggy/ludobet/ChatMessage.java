package com.Saggy.ludobet;

public class ChatMessage {

    private String messageText;
    private String messageTime;
    private String messageUser;
    private String showname;

    public void setShowname(String showname) {
        this.showname = showname;
    }

    public String getShowname() {
        return showname;
    }

    private android.widget.Button button;

    static ChatMessage chatMessage;

    public ChatMessage(String messageUser, String messageText, String messageTime) {
//    public ChatMessage(String messageUser, String messageText, String messageTime, String showname) {

        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTime = messageTime;
//        this.showname = showname;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public void setButton(android.widget.Button button) {
        this.button = button;
    }

    public android.widget.Button getButton() {
        return button;
    }

    public static ChatMessage getInstance(){

        return chatMessage;
    }

    public ChatMessage() {
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }
}
