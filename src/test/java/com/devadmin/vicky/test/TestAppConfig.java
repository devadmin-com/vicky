package com.devadmin.vicky.test;

import com.devadmin.vicky.service.slack.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Used for Testing...
 * <p>
 * <p>Returns testing class for MessageService...
 */
@Configuration
public class TestAppConfig {

    /**
     * @return testing class for MessageService...
     */
    @Bean
    public MessageService getMessageService() {
        return new TestMessageService();
    }
}