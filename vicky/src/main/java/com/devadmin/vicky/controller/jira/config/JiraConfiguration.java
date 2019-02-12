package com.devadmin.vicky.controller.jira.config;

import com.devadmin.jira.BasicCredentials;
import com.devadmin.jira.JiraClient;
import com.devadmin.jira.JiraException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/** Configuration class for jira module */
@Configuration
@EnableConfigurationProperties(JiraProperties.class)
public class JiraConfiguration {

  private JiraProperties jiraProperties;

  public JiraConfiguration(JiraProperties jiraProperties) {
    this.jiraProperties = jiraProperties;
  }

  /** @return resttemplate bean which used to perform http requests */
  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  /**
   * This method creates JiraClient bean based on application properties
   *
   * @return JiraClient instance
   */
  @Bean
  public JiraClient getJiraClient() {
    BasicCredentials basicCredentials =
        new BasicCredentials(jiraProperties.getUsername(), jiraProperties.getPassword());
    JiraClient jiraClient = null;
    try {
      jiraClient = new JiraClient(jiraProperties.getCloudUrl(), basicCredentials);
    } catch (JiraException e) {
      e.printStackTrace();
    }
    return jiraClient;
  }
}
