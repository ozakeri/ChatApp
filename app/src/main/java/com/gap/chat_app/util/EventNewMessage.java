package com.gap.chat_app.util;

public class EventNewMessage {

    private int countMessage;

    public EventNewMessage(int countMessage) {
        this.countMessage = countMessage;
    }

    public int getCountMessage() {
        return countMessage;
    }

    public void setCountMessage(int countMessage) {
        this.countMessage = countMessage;
    }
}
