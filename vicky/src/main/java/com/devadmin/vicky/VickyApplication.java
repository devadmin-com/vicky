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
@ComponentScan("com.devadmin") //TODO this will component scan all the imported modules as well? can we not just scan com.devadmin.vicky which is in the package in this module?
@EnableConfigurationProperties({SlackProperties.class, JiraProperties.class}) //TODO: this creates a dependency on SlackProperties and JiraProperties here - can this line not be in those components i.e. the components load the properties they need? (I'm not a spring expert so I don't know)
public class VickyApplication {

  /**
   * Main method, used to run the application.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(VickyApplication.class, args);
  }

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  } //TODO:what is this for?
}
