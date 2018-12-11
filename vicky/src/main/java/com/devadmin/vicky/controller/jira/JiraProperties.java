package com.devadmin.vicky.controller.jira;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * The configuration of JIRA...
 *
 * requires the following three keys in your application.yml:
 *
 *     cloud-url: <yourdomain.atlassian.net> - the URL of the JIRA instance
 *     username: <your jira username>
 *     password: <your jira password>
 *
 */
@ConfigurationProperties(prefix = "jira")
public class JiraProperties {


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

  /**
   * This method create JiraClient bean based on application properties
   *
   * @return JiraClient instance
   */
  @Bean
  public JiraClient getJiraClient() {
    BasicCredentials basicCredentials = new BasicCredentials(getUsername(), getPassword());
    return new JiraClient(getCloudUrl(), basicCredentials);
  }
}
