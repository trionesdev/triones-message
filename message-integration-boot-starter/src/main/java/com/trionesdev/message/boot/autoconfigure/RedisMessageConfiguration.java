package com.trionesdev.message.boot.autoconfigure;

import com.trionesdev.message.core.Message;
import com.trionesdev.message.core.MessageContainer;
import com.trionesdev.message.integration.redis.RedisMessageContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnMissingBean({MessageContainer.class})
@Conditional({MessageCondition.class})
public class RedisMessageConfiguration {
    private final MessageProperties messageProperties;
    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, Message>> recordStreamMessageListenerContainer(
            RedisConnectionFactory factory) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, Message>> options =
                StreamMessageListenerContainer
                        .StreamMessageListenerContainerOptions
                        .builder()
                        .pollTimeout(Duration.ofSeconds(1)).targetType(Message.class)
                        .build();
        StreamMessageListenerContainer<String, ObjectRecord<String, Message>> listenerContainer = StreamMessageListenerContainer.create(factory, options);
        listenerContainer.start();
        return listenerContainer;
    }



    @Bean
    public MessageContainer redisMessageContainer(StringRedisTemplate redisTemplate,
                                                  RedisMessageListenerContainer redisMessageListenerContainer,
                                                  StreamMessageListenerContainer<String, ObjectRecord<String, Message>> streamMessageListenerContainer) {
        return new RedisMessageContainer(messageProperties.getRedis(),redisTemplate, redisMessageListenerContainer, streamMessageListenerContainer);
    }
}
