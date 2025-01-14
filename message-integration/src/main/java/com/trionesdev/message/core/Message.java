package com.trionesdev.message.core;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Map<String, String> headers;
    private String topic;
    private String payload;

    public <T> T getPayloadAs(Class<T> clazz) {
        return JSON.parseObject(payload, clazz);
    }
}
