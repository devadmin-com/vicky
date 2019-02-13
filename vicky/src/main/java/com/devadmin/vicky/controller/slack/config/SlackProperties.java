package com.devadmin.vicky.controller.slack.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;


/**
 * The configuration of slack client. Requires the following keys in application.yml:
 *
 * <p>api-url: <URL of instance. e.g. http://devadmin.slack.com/api> token: bot: verification:
 * webhook: incoming: directMessage: white: the name of the jira project which have to have slack
 * channel with the same name
 */
@ConfigurationProperties(prefix = "slack")
public class SlackProperties {

  private String apiUrl;
  @NestedConfigurationProperty private Token token;
  @NestedConfigurationProperty private Webhook webhook;

  public String getApiUrl() {
    return apiUrl;
  }

  public void setApiUrl(String apiUrl) {
    this.apiUrl = apiUrl;
  }

  public Token getToken() {
    return token;
  }

  public void setToken(Token token) {
    this.token = token;
  }

  public Webhook getWebhook() {
    return webhook;
  }

  public void setWebhook(Webhook webhook) {
    this.webhook = webhook;
  }

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
