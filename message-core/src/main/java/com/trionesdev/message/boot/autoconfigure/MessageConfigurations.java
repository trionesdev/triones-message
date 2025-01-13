package com.trionesdev.message.boot.autoconfigure;

import org.springframework.util.Assert;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class MessageConfigurations {
    private static final Map<MessageType, String> MAPPINGS;

    static {
        Map<MessageType, String> mappings = new EnumMap<>(MessageType.class);

        MAPPINGS = Collections.unmodifiableMap(mappings);
    }

    static String getConfigurationClass(MessageType lockType) {
        String configurationClassName = MAPPINGS.get(lockType);
        Assert.state(configurationClassName != null, () -> "Unknown message type " + lockType);
        return configurationClassName;
    }

    static MessageType getType(String configurationClassName) {
        for (Map.Entry<MessageType, String> entry : MAPPINGS.entrySet()) {
            if (entry.getValue().equals(configurationClassName)) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException("Unknown configuration class " + configurationClassName);
    }


}
