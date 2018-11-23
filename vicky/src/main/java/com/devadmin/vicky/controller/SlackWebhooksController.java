package com.devadmin.vicky.controller;

import com.devadmin.jira.JiraException;
import com.devadmin.jira.webhook.JiraWebhookEvent;
import com.devadmin.vicky.service.SlackWebhookService;
import java.text.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class contain methods which received events from JIRA
 */
@RestController
public class SlackWebhooksController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SlackWebhooksController.class);

  private SlackWebhookService slackWebhookService;

  @Autowired
  public SlackWebhooksController(SlackWebhookService slackWebhookService) {
    this.slackWebhookService = slackWebhookService;
  }

  @PostMapping("/ticket")
  public void incomingIssueEvent(@RequestBody JiraWebhookEvent webhookEvent) throws JiraException, ParseException {
    slackWebhookService.eventProcessing(webhookEvent);
  }
}
