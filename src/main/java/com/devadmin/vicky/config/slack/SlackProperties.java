package com.devadmin.vicky.config.slack;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;


/**
 * The configuration of slack client. Requires the following keys in application.yml:
 * <p>
 * <p>api-url: <URL of instance. e.g. http://devadmin.slack.com/api> token: bot: verification:
 * webhook: incoming: directMessage: white: the name of the jira project which have to have slack
 * channel with the same name
 */
@ConfigurationProperties(prefix = "slack")
@Data
public class SlackProperties {

    private String apiUrl;
    @NestedConfigurationProperty
    private Token token;

    @Data
    public static class Token {
        private String bot;
        private String verification;
    }

    public static class Webhook {
        private Map<String, String> incoming;

        public Map<String, String> getIncoming() {
            return incoming;
        }

        public void setIncoming(Map<String, String> incoming) {
            this.incoming = incoming;
        }
    }
}
