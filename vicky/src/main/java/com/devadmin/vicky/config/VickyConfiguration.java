package com.devadmin.vicky.config;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration file for Vicky
 */
@Configuration
@EnableConfigurationProperties(VickyProperties.class)
public class VickyConfiguration {

  private final VickyProperties vickyProperties;

  @Autowired
  public VickyConfiguration(VickyProperties vickyProperties) {
    this.vickyProperties = vickyProperties;
  }

  /**
   * This method create RestTemplate bean
   *
   * @return RestTemplate instance
   */
  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  /**
   * This method create JiraClient bean based on application properties
   *
   * @return JiraClient instance
   */
  @Bean
  public JiraClient getJiraClient() {
    BasicCredentials basicCredentials = new BasicCredentials(vickyProperties.getJira().getUsername(), vickyProperties.getJira().getPassword());
    return new JiraClient(vickyProperties.getJira().getCloudUrl(), basicCredentials);
  }

  /**
   * This method creates ApplicationEventMulticaster bean in order to support asynchronous event handler
   *
   * @return ApplicationEventMulticaster instance
   */
  @Bean(name = "applicationEventMulticaster")
  public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
    SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
    eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
    return eventMulticaster;
  }
}
