package com.trionesdev.message.spring;

import com.trionesdev.message.core.Message;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class TrionesSpringMessageEvent extends ApplicationEvent {
    private Message message;
    public TrionesSpringMessageEvent(Object source) {
        super(source);
        this.message = (Message) source;
    }

    public TrionesSpringMessageEvent(Object source, Clock clock) {
        super(source, clock);
        this.message = (Message) source;
    }
}
