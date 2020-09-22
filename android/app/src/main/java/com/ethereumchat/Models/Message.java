package com.ethereumchat.Models;

import java.io.Serializable;

public class Message implements Serializable {

    String name;
    String timestamp;
    String message;
    String isImage;
    String fromPublicKey;
    String toPublicKey;

    public Message(String name, String timestamp, String message, String isImage, String fromPublicKey, String toPublicKey) {
        this.name = name;
        this.timestamp = timestamp;
        this.message = message;
        this.isImage = isImage;
        this.fromPublicKey = fromPublicKey;
        this.toPublicKey = toPublicKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return isImage;
    }

    public void setImage(String image) {
        isImage = image;
    }

    public String getFromPublicKey() {
        return fromPublicKey;
    }

    public void setFromPublicKey(String fromPublicKey) {
        this.fromPublicKey = fromPublicKey;
    }

    public String getToPublicKey() {
        return toPublicKey;
    }

    public void setToPublicKey(String toPublicKey) {
        this.toPublicKey = toPublicKey;
    }

    @Override
    public String toString() {
        return "Message{" +
                "timestamp='" + timestamp + '\'' +
                ", message='" + message + '\'' +
                ", isImage='" + isImage + '\'' +
                ", fromPublicKey='" + fromPublicKey + '\'' +
                ", toPublicKey='" + toPublicKey + '\'' +
                '}';
    }
}
