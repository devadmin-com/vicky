package com.devadmin.vicky.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.client.RestTemplate;

/*
 * Configuration file for Vicky
 *
@Configuration
@EnableConfigurationProperties(JiraProperties.class)
public class VickyConfiguration {

  @Autowired
  public VickyConfiguration(JiraProperties jiraProperties) {
    this.jiraProperties = jiraProperties;
  }

  /**
   * This method create RestTemplate bean
   *
   * @return RestTemplate instance
   *
  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }



  /**
   * This method creates ApplicationEventMulticaster bean in order to support asynchronous event handler
   *
   * @return ApplicationEventMulticaster instance
   *
  @Bean(name = "applicationEventMulticaster")
  public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
    SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
    eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
    return eventMulticaster;
  }
}
*/