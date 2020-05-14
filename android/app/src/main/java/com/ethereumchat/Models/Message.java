package com.ethereumchat.Models;

import java.io.Serializable;

public class Message implements Serializable {

    String timestamp;
    String message;
    Boolean isImage;
    String fromPublicKey;
    String toPublicKey;

    public Message(String timestamp, String message, Boolean isImage, String fromPublicKey, String toPublicKey) {
        this.timestamp = timestamp;
        this.message = message;
        this.isImage = isImage;
        this.fromPublicKey = fromPublicKey;
        this.toPublicKey = toPublicKey;
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

    public Boolean getImage() {
        return isImage;
    }

    public void setImage(Boolean image) {
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
}
