package com.example.gungde.reminder_medicine.model;

import com.google.gson.annotations.SerializedName;

public class UpdateArtikelModel {

    @SerializedName("messages")
    private String messages;
    @SerializedName("status")
    private boolean status;

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
