package com.devadmin.vicky.service;

import com.devadmin.vicky.bot.VickyBot;
import com.devadmin.vicky.service.dto.EventDto;
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

  private VickyBot vickyBot;

  private EventDto eventDto;

  private String message;
  private String userDisplayName;

  public IssueCommentTracker(EventDto eventDto, String message, JiraClient jiraClient, VickyBot vickyBot,
      String userDisplayName) {
    this.message = message;
    this.vickyBot = vickyBot;
    this.eventDto = eventDto;
    this.userDisplayName = userDisplayName;
    this.jiraClient = jiraClient;
    timer.scheduleAtFixedRate(new Reminder(), DELAY, PERIOD);
  }

  class Reminder extends TimerTask {

    public void run() {
      try {
        List<Comment> comments = jiraClient.getIssue(eventDto.getIssue().getId()).getComments();
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