package com.trionesdev.message.boot.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "triones.message")
public class MessageProperties {
    private MessageType type = MessageType.SPRING;
}
