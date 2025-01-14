package com.trionesdev.message.integration.redis;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class RedisMessageProperties {
    private String group = "triones-redis-message-group";
    private String consumerName = "";
    private String streamKey = "triones-redis-message-group";

    public String getConsumerName() {
        return StringUtils.hasText(consumerName)?consumerName:"triones-redis-message-consumer";
    }
}
