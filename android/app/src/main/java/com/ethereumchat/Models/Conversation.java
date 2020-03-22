package com.ethereumchat.Models;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Conversation implements Serializable {
    String name;
    String publicKey;
    String profileInString;
    String lastMessage;
    String lastMessageTimestamp;
    String read;

    public Conversation(String name, String publicKey, String profileInString, String lastMessage, String lastMessageTimestamp, String read) {
        this.name = name;
        this.publicKey = publicKey;
        this.profileInString = profileInString;
        this.lastMessage = lastMessage;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.read = read;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getProfileInString() {
        return profileInString;
    }

    public void setProfileInString(String profileInString) {
        this.profileInString = profileInString;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public void setLastMessageTimestamp(String lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    @NonNull
    @Override
    public String toString() {
        return "Conversation{" +
                "name='" + name + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", profileInString='" + profileInString + '\'' +
                ", lastMessage='" + lastMessage + '\'' +
                ", lastMessageTimestamp='" + lastMessageTimestamp + '\'' +
                ", read='" + read + '\'' +
                '}';
    }
}
