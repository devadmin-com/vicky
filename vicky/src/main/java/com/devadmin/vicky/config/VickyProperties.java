package com.devadmin.vicky.config;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "vicky")
public class VickyProperties {

  @NestedConfigurationProperty
  private Jira jira;

  @NestedConfigurationProperty
  private Slack slack;

  public Jira getJira() {
    return jira;
  }

  public Slack getSlack() {
    return slack;
  }

  public void setJira(Jira jira) {
    this.jira = jira;
  }

  public void setSlack(Slack slack) {
    this.slack = slack;
  }

  public static class Jira {

    private String cloudUrl;
    private String username;
    private String password;

    public String getCloudUrl() {
      return cloudUrl;
    }

    public void setCloudUrl(String cloudUrl) {
      this.cloudUrl = cloudUrl;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }

  public static class Slack {
    private String apiUrl;
    @NestedConfigurationProperty
    private Token token;
    @NestedConfigurationProperty
    private Webhook webhook;

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
}