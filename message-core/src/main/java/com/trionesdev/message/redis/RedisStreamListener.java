package com.trionesdev.message.redis;

import com.trionesdev.message.core.Message;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;

public class RedisStreamListener implements StreamListener<String, ObjectRecord<String, Message>> {
    @Override
    public void onMessage(ObjectRecord<String, Message> message) {

    }
}
