package com.trionesdev.message.redis;

import com.alibaba.fastjson2.JSON;
import com.trionesdev.message.core.Message;
import com.trionesdev.message.core.MessageContainer;
import com.trionesdev.message.core.MessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class RedisMessageContainer implements MessageContainer {
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final Map<String, MessageListener> queueListeners = new HashMap<>();

    @Override
    public void publish(Message message) {
        stringRedisTemplate.convertAndSend(message.getTopic(), message);
    }

    @Override
    public void publish(String topic, Message message) {
        message.setTopic(topic);
        stringRedisTemplate.convertAndSend(topic, message);
    }

    @Override
    public void broadcast(Message message) {
        Record<String,Message> record = StreamRecords.objectBacked(message).withStreamKey(message.getTopic());
        stringRedisTemplate.opsForStream().add(record);
    }

    @Override
    public void broadcast(String topic, Message message) {
        message.setTopic(topic);
        Record<String,Message> record = StreamRecords.objectBacked(message).withStreamKey(topic);
        stringRedisTemplate.opsForStream().add(record);
    }

    @Override
    public void addQueueListener(MessageListener listener, String topic) {
        queueListeners.put(topic, listener);
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
}
