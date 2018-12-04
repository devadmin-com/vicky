package com.devadmin.vicky.config;

import com.devadmin.vicky.util.DozerMapper;
import com.devadmin.vicky.util.impl.DozerMapperImpl;
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

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  public DozerMapper dozerMapper() {
    return new DozerMapperImpl();
  }

  @Bean
  public JiraClient getJiraClient() {
    BasicCredentials basicCredentials = new BasicCredentials(vickyProperties.getJira().getUsername(), vickyProperties.getJira().getPassword());
    return new JiraClient(vickyProperties.getJira().getCloudUrl(), basicCredentials);
  }

  @Bean(name = "applicationEventMulticaster")
  public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
    SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
    eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
    return eventMulticaster;
  }
}
