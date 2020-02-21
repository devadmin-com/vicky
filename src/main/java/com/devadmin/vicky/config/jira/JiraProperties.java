package com.devadmin.vicky.config.jira;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The configuration of JIRA...
 * <p>
 * <p>requires the following three keys in your application.yml:
 * <p>
 * <p>cloud-url: <yourdomain.atlassian.net> - the URL of the JIRA instance username: <your jira
 * username> password: <your jira password>
 */
@ConfigurationProperties(prefix = "jira")
@Data
public class JiraProperties {

    private String cloudUrl;
    private String username;
    private String password;

}
