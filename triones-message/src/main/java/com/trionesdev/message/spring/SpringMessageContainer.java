package com.trionesdev.message.spring;

import com.trionesdev.message.core.Message;
import com.trionesdev.message.core.MessageContainer;
import com.trionesdev.message.core.MessageListener;
import org.springframework.context.ApplicationEventPublisher;

import java.util.HashMap;
import java.util.Map;

public class SpringMessageContainer implements MessageContainer {
    private ApplicationEventPublisher eventPublisher;
    private final Map<String, MessageListener> messageListeners = new HashMap<>();

    @Override
    public void publish(Message message) {

    }

    @Override
    public void publish(String topic, Message message) {
        message.
    }

    @Override
    public void addListener(MessageListener listener, String topic) {
        messageListeners.put(topic, listener);
    }


}
