package com.devadmin.vicky.controller.slack.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

/**
 * The configuration of slack client. Requires the following keys in application.yml:
 *
 *   api-url: <URL of instance. e.g. http://devadmin.slack.com/api>
 *   token:
 *     bot:
 *     verification:
 *   webhook:
 *     incoming:
 *       directMessage:
 *       white: the name of the jira project which have to have slack channel with the same name
 *       TODO: I don't understand above? have to pre-define all channels that vicky works on? I am pretty sure we don't do that in current implementation
 */
@ConfigurationProperties(prefix = "slack")
public class SlackProperties {

        private String apiUrl;
        @NestedConfigurationProperty
        private Token token;
        @NestedConfigurationProperty
        private Webhook webhook;

    //TODO: Javadoc
        public String getApiUrl() {
            return apiUrl;
        }

    //TODO: Javadoc
        public void setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
        }

    //TODO: Javadoc
        public Token getToken() {
            return token;
        }

    //TODO: Javadoc
        public void setToken(Token token) {
            this.token = token;
        }

    //TODO: Javadoc
        public Webhook getWebhook() {
            return webhook;
        }

    //TODO: Javadoc
        public void setWebhook(Webhook webhook) {
            this.webhook = webhook;
        }

    //TODO: Javadoc
        public static class Token {
            private String bot;
            private String verification;

            public String getBot() {
                return bot;
            }

            public void setBot(String bot) {
                this.bot = bot;
            }

            public String getVerification() {
                return verification;
            }

            public void setVerification(String verification) {
                this.verification = verification;
            }
        }

    //TODO: Javadoc
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
