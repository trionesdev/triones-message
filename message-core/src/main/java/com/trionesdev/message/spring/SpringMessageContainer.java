package com.trionesdev.message.spring;

import com.trionesdev.message.core.Message;
import com.trionesdev.message.core.MessageContainer;
import com.trionesdev.message.core.MessageListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class SpringMessageContainer implements MessageContainer {
    private final ApplicationEventPublisher eventPublisher;
    @Getter
    private final Map<String, MessageListener> messageListeners = new HashMap<>();

    @Override
    public void publish(Message message) {
        eventPublisher.publishEvent(message);
    }

    @Override
    public void publish(String topic, Message message) {
        message.setTopic(topic);
        publish(message);
    }

    @Override
    public void broadcast(Message message) {
        publish(message);
    }

    @Override
    public void broadcast(String topic, Message message) {
        message.setTopic(topic);
        broadcast(message);
    }

    @Override
    public void addQueueListener(MessageListener listener, String topic) {
        messageListeners.put(topic, listener);
    }

    @Override
    public void addBroadcastListener(MessageListener listener, String topic) {
        messageListeners.put(topic, listener);
    }

}
