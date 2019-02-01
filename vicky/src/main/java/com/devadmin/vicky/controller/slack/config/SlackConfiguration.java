package com.devadmin.vicky.controller.slack.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SlackProperties.class)
public class SlackConfiguration {

}
