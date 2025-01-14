package com.trionesdev.message.integration.spring;

import com.trionesdev.message.core.Message;
import com.trionesdev.message.core.MessageContainer;
import com.trionesdev.message.core.MessageListener;
import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;

import java.util.HashMap;
import java.util.Map;


public class SpringMessageContainer implements MessageContainer {
    private final ApplicationEventPublisher eventPublisher;

    public SpringMessageContainer(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Getter
    private final Map<String, MessageListener> messageListeners = new HashMap<>();

    @Override
    public void publish(Message message) {
        eventPublisher.publishEvent(new SpringMessageEvent(message));
    }

    @Override
    public void publish(String topic, String payload) {

        Message message = new Message();
        message.setTopic(topic);
        message.setPayload(payload);
        publish(message);
    }

    @Override
    public void broadcast(Message message) {
        publish(message);
    }

    @Override
    public void broadcast(String topic, String payload) {
        Message message = new Message();
        message.setTopic(topic);
        message.setPayload(payload);
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
