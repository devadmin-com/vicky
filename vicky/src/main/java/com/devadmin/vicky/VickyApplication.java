package com.devadmin.vicky;

import com.devadmin.vicky.controller.jira.JiraProperties;
import com.devadmin.vicky.controller.slack.SlackProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * Main class for Vicky application
 */
@SpringBootApplication
@ComponentScan("com.devadmin")
@EnableConfigurationProperties({JiraProperties.class, SlackProperties.class})
public class VickyApplication {

  @Bean
  public RestTemplate getRestTemplate(){
    return  new RestTemplate();
  }

  /**
   * Main method, used to run the application.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(VickyApplication.class, args);
  }
}
