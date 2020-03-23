package com.ethereumchat.Models;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Contact implements Serializable {
    String name;
    String publicKey;
    String profileInString;

    public Contact(String name, String publicKey, String profileInString) {
        this.name = name;
        this.publicKey = publicKey;
        this.profileInString = profileInString;
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

    @NonNull
    @Override
    public String toString() {
        return "Conversation{" +
                "name='" + name + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", profileInString='" + profileInString + '\'' +
                '}';
    }
}
