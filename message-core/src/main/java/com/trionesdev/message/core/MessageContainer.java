package com.trionesdev.message.core;

public interface MessageContainer {
    void publish(Message message);

    void publish(String topic, Message message);

    void broadcast(Message message);

    void broadcast(String topic, Message message);

    void addQueueListener(MessageListener listener, String topic);

    void addBroadcastListener(MessageListener listener, String topic);
}
