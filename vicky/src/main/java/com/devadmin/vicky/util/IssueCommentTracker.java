package com.devadmin.vicky.util;

import com.devadmin.vicky.controller.slack.SlackController;
import com.devadmin.vicky.controller.jira.model.JiraEventModel;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import net.rcarz.jiraclient.Comment;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

/**
 * This class contain issue comment tracker functionality
 */
public class IssueCommentTracker {

  private static final long DELAY = 1000L * 60L * 60L * 24L;
  private static final long PERIOD = 1000L * 60L * 60L * 6L;

  private Timer timer = new Timer();

  private JiraClient jiraClient;

  private SlackController slackController;

  private JiraEventModel jiraEventModel;

  private String message;
  private String userDisplayName;

  public IssueCommentTracker(JiraEventModel jiraEventModel, String message, JiraClient jiraClient, SlackController slackController,
      String userDisplayName) {
    this.message = message;
    this.slackController = slackController;
    this.jiraEventModel = jiraEventModel;
    this.userDisplayName = userDisplayName;
    this.jiraClient = jiraClient;
    timer.scheduleAtFixedRate(new Reminder(), DELAY, PERIOD);
  }

  class Reminder extends TimerTask {

    public void run() {
      try {
        List<Comment> comments = jiraClient.getIssue(jiraEventModel.getTask().getId()).getComments();
        if (comments.size() == 0) {
          slackController.sendDirectMessageToBot(message, userDisplayName);
        } else {
          timer.cancel();
        }
      } catch (JiraException e) {
        e.printStackTrace();
      }
    }
  }
}