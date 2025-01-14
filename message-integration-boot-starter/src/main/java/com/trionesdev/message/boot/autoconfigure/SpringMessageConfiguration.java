package com.trionesdev.message.boot.autoconfigure;

import com.trionesdev.message.core.MessageContainer;
import com.trionesdev.message.integration.spring.SpringMessageContainer;
import com.trionesdev.message.integration.spring.SpringMessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@ConditionalOnMissingBean({MessageContainer.class})
@Conditional({MessageCondition.class})
public class SpringMessageConfiguration {

    @Bean
    public SpringMessageContainer messageContainer(ApplicationEventPublisher eventPublisher) {
        return new SpringMessageContainer(eventPublisher);
    }

    @Bean
    public SpringMessageListener springMessageListener(SpringMessageContainer messageContainer) {
        return new SpringMessageListener(messageContainer);
    }
}
