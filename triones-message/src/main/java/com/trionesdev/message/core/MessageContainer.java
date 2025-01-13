package com.trionesdev.message.core;

public interface MessageContainer {
    void publish(Message message);
    void publish(String topic,Message message);
    void addListener(MessageListener listener,String topic);
}
