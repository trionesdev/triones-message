package com.trionesdev.message.spring;

import com.trionesdev.message.core.Message;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class SpringMessageEvent extends ApplicationEvent {
    private final Message message;

    public SpringMessageEvent(Object source) {
        super(source);
        this.message = (Message) source;
    }

    public SpringMessageEvent(Object source, Clock clock) {
        super(source, clock);
        this.message = (Message) source;
    }
}
