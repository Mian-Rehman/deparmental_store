package com.rehman.newtrend.Chat;

public class ChatModel
{
    String message;
    String senderUID;
    String chatKey;
    String chatType;

    public ChatModel(String message, String senderUID, String chatKey, String chatType) {
        this.message = message;
        this.senderUID = senderUID;
        this.chatKey = chatKey;
        this.chatType = chatType;
    }

    public ChatModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderUID() {
        return senderUID;
    }

    public void setSenderUID(String senderUID) {
        this.senderUID = senderUID;
    }

    public String getChatKey() {
        return chatKey;
    }

    public void setChatKey(String chatKey) {
        this.chatKey = chatKey;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }
}
