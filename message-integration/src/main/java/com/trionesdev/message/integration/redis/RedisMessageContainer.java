package com.trionesdev.message.integration.redis;

import com.alibaba.fastjson2.JSON;
import com.trionesdev.message.core.Message;
import com.trionesdev.message.core.MessageContainer;
import com.trionesdev.message.core.MessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class RedisMessageContainer implements MessageContainer {
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final StreamMessageListenerContainer<String, ObjectRecord<String, Message>> streamMessageListenerContainer;

    public RedisMessageContainer(StringRedisTemplate stringRedisTemplate, RedisMessageListenerContainer redisMessageListenerContainer, StreamMessageListenerContainer<String, ObjectRecord<String, Message>> streamMessageListenerContainer) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisMessageListenerContainer = redisMessageListenerContainer;
        this.streamMessageListenerContainer = streamMessageListenerContainer;
    }

    @Override
    public void publish(Message message) {
        Record<String, Message> record = StreamRecords.objectBacked(message).withStreamKey("message");
        stringRedisTemplate.opsForStream().add(record);
    }

    @Override
    public void publish(String topic, Message message) {
        message.setTopic(topic);
        publish(message);
    }

    @Override
    public void broadcast(Message message) {
        stringRedisTemplate.convertAndSend(message.getTopic(), JSON.toJSONString(message));
    }

    @Override
    public void broadcast(String topic, Message message) {
        message.setTopic(topic);
        broadcast(message);
    }

    @Override
    public void addQueueListener(MessageListener listener, String topic) {
        streamListenerInit(stringRedisTemplate);
        streamMessageListenerContainer.receiveAutoAck(
                Consumer.from("test", "localhost"),
                StreamOffset.create("message", ReadOffset.lastConsumed()),
                new StreamListener<String, ObjectRecord<String, Message>>() {
                    @Override
                    public void onMessage(ObjectRecord<String, Message> message) {
                        listener.onMessage(message.getValue());
                    }
                }
        );
    }

    @Override
    public void addBroadcastListener(MessageListener listener, String topic) {
        redisMessageListenerContainer.addMessageListener(new org.springframework.data.redis.connection.MessageListener() {
            @Override
            public void onMessage(org.springframework.data.redis.connection.Message message, byte[] pattern) {
                listener.onMessage(JSON.parseObject(message.getBody(), Message.class));
            }
        }, PatternTopic.of(topic));
    }

    private void streamListenerInit(StringRedisTemplate redisTemplate) {
        List<String> consumers = new ArrayList<>();
        consumers.add("test");
        StreamInfo.XInfoGroups infoGroups = null;
        try {
            infoGroups = redisTemplate.opsForStream().groups("message");
        } catch (RedisSystemException | InvalidDataAccessApiUsageException ex) {
            log.error("group key not exist or commend error", ex);
        }

        for (String consumer : consumers) {
            boolean consumerExist = false;
            if (Objects.nonNull(infoGroups)) {
                if (infoGroups.stream().anyMatch(t -> Objects.equals(consumer, t.groupName()))) {
                    consumerExist = true;
                }
            }
            if (!consumerExist) {
                redisTemplate.opsForStream().createGroup("message", consumer);
            }
        }

    }
}
