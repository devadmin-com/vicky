package com.devadmin.vicky.controller.jira.config;

import lombok.AllArgsConstructor;
import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for jira module
 */
@Configuration
@EnableConfigurationProperties(JiraProperties.class)
@AllArgsConstructor
public class JiraConfiguration {

    private final JiraProperties jiraProperties;

    /**
     * @return resttemplate bean which used to perform http requests
     */
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
        JiraClient jiraClient;
        try {
            jiraClient = new JiraClient(jiraProperties.getCloudUrl(), basicCredentials);
        } catch (JiraException e) {
            throw new RuntimeException("Can't create jira client");
        }
        return jiraClient;
    }
}
