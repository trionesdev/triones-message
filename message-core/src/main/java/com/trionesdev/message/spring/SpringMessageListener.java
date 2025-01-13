package com.trionesdev.message.spring;

import com.trionesdev.message.core.Message;
import com.trionesdev.message.core.MessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;

@RequiredArgsConstructor
public class SpringMessageListener implements ApplicationListener<SpringMessageEvent> {

    private final SpringMessageContainer messageContainer;

    @Override
    public void onApplicationEvent(SpringMessageEvent event) {
        Message message = event.getMessage();
        MessageListener listener = messageContainer.getMessageListeners().get(message.getTopic());
        listener.onMessage(message);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
