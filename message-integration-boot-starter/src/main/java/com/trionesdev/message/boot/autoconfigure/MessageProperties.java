package com.trionesdev.message.boot.autoconfigure;

import com.trionesdev.message.integration.redis.RedisMessageProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "triones.message")
public class MessageProperties {
    private MessageType type = MessageType.SPRING;
    private RedisMessageProperties redis = new RedisMessageProperties();
}
