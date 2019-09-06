package com.devadmin.vicky.controller.slack.config;

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
public class SlackProperties {

    private String apiUrl;
    @NestedConfigurationProperty
    private Token token;

    public SlackProperties() {
    }

    public String getApiUrl() {
        return this.apiUrl;
    }

    public Token getToken() {
        return this.token;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SlackProperties)) return false;
        final SlackProperties other = (SlackProperties) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$apiUrl = this.getApiUrl();
        final Object other$apiUrl = other.getApiUrl();
        if (this$apiUrl == null ? other$apiUrl != null : !this$apiUrl.equals(other$apiUrl)) return false;
        final Object this$token = this.getToken();
        final Object other$token = other.getToken();
        if (this$token == null ? other$token != null : !this$token.equals(other$token)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SlackProperties;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $apiUrl = this.getApiUrl();
        result = result * PRIME + ($apiUrl == null ? 43 : $apiUrl.hashCode());
        final Object $token = this.getToken();
        result = result * PRIME + ($token == null ? 43 : $token.hashCode());
        return result;
    }

    public String toString() {
        return "SlackProperties(apiUrl=" + this.getApiUrl() + ", token=" + this.getToken() + ")";
    }

    public static class Token {
        private String bot;
        private String verification;

        public Token() {
        }

        public String getBot() {
            return this.bot;
        }

        public String getVerification() {
            return this.verification;
        }

        public void setBot(String bot) {
            this.bot = bot;
        }

        public void setVerification(String verification) {
            this.verification = verification;
        }

        public boolean equals(final Object o) {
            if (o == this) return true;
            if (!(o instanceof Token)) return false;
            final Token other = (Token) o;
            if (!other.canEqual((Object) this)) return false;
            final Object this$bot = this.getBot();
            final Object other$bot = other.getBot();
            if (this$bot == null ? other$bot != null : !this$bot.equals(other$bot)) return false;
            final Object this$verification = this.getVerification();
            final Object other$verification = other.getVerification();
            if (this$verification == null ? other$verification != null : !this$verification.equals(other$verification))
                return false;
            return true;
        }

        protected boolean canEqual(final Object other) {
            return other instanceof Token;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $bot = this.getBot();
            result = result * PRIME + ($bot == null ? 43 : $bot.hashCode());
            final Object $verification = this.getVerification();
            result = result * PRIME + ($verification == null ? 43 : $verification.hashCode());
            return result;
        }

        public String toString() {
            return "SlackProperties.Token(bot=" + this.getBot() + ", verification=" + this.getVerification() + ")";
        }
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
