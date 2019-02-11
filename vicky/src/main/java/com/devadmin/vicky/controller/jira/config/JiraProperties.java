package com.devadmin.vicky.controller.jira.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The configuration of JIRA...
 *
 * <p>requires the following three keys in your application.yml:
 *
 * <p>cloud-url: <yourdomain.atlassian.net> - the URL of the JIRA instance username: <your jira
 * username> password: <your jira password>
 */
@ConfigurationProperties(prefix = "jira")
public class JiraProperties {

  private String cloudUrl;
  private String username;
  private String password;

  // TODO: Javadoc
  public String getCloudUrl() {
    return cloudUrl;
  }

  // TODO: Javadoc
  public void setCloudUrl(String cloudUrl) {
    this.cloudUrl = cloudUrl;
  }

  // TODO: Javadoc
  public String getUsername() {
    return username;
  }

  // TODO: Javadoc
  public void setUsername(String username) {
    this.username = username;
  }

  // TODO: Javadoc
  public String getPassword() {
    return password;
  }

  // TODO: Javadoc
  public void setPassword(String password) {
    this.password = password;
  }
}
