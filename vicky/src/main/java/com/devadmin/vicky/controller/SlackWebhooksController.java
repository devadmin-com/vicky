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
 * This is a SlackProperties Webhook sample. Webhooks are nothing but POST calls to SlackProperties with data relevant
 * to your users. You can send the data in the POST call in either ways: 1) Send as a JSON string as the payload
 * parameter in a POST request 2) Send as a JSON string as the body of a POST request
 *
 * @author ramswaroop
 * @version 1.0.0, 21/06/2016
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
