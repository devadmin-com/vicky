package com.devadmin.vicky.service;

import com.devadmin.jira.Comment;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.webhook.JiraWebhookEvent;
import com.devadmin.vicky.bot.VickyBot;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contain issue comment tracker functionality @todo what does that mean? what is a commetn tracker?
 *
 *
 */
public class IssueCommentTracker {

  private static final long DELAY = 1000L * 60L * 60L * 24L;
  private static final long PERIOD = 1000L * 60L * 60L * 6L;

  private Timer timer = new Timer();

  private JiraRestService jiraRestService;

  private VickyBot vickyBot;

  private JiraWebhookEvent webhookEvent;

  private String message;
  private String userDisplayName;

  public IssueCommentTracker(JiraWebhookEvent webhookEvent, String message, JiraRestService jiraRestService,
      VickyBot vickyBot, String userDisplayName) {
    this.message = message;
    this.vickyBot = vickyBot;
    this.webhookEvent = webhookEvent;
    this.userDisplayName = userDisplayName;
    this.jiraRestService = jiraRestService;
    timer.scheduleAtFixedRate(new Reminder(), DELAY, PERIOD);
  }

  class Reminder extends TimerTask { //@todo what is this for?

    private final Logger LOGGER = LoggerFactory.getLogger(Reminder.class);

    public void run() {
      try {
        List<Comment> comments = jiraRestService.getCommentsByIssueId(webhookEvent.getIssue().getId());
        if (comments.size() == 0) {
          vickyBot.sendDirectMessageToBot(message, userDisplayName);
        } else {
          timer.cancel();
        }
      } catch (JiraException e) {
        e.printStackTrace();
      }
    }
  }
}