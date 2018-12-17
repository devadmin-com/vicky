package com.devadmin.vicky.config;

import com.devadmin.vicky.config.VickyProperties.Jira;
import com.devadmin.vicky.config.VickyProperties.Slack;
import com.devadmin.vicky.config.VickyProperties.Slack.Token;
import com.devadmin.vicky.config.VickyProperties.Slack.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration file for Vicky
 * @todo why do we need this and VickyProperties class both?
 */
@Configuration
@EnableConfigurationProperties(VickyProperties.class)
public class VickyConfiguration {

  private final VickyProperties vickyProperties;

  @Autowired
  public VickyConfiguration(VickyProperties vickyProperties) {
    this.vickyProperties = vickyProperties;
  }

  @Bean
  public Jira getJira() {
    return vickyProperties.getJira();
  }

  @Bean
  public Slack getSlack() {
    return vickyProperties.getSlack();
  }

  @Bean
  public Token getToken() {
    return vickyProperties.getSlack().getToken();
  }

  @Bean
  public Webhook getWebhook() {
    return vickyProperties.getSlack().getWebhook();
  }

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

}
