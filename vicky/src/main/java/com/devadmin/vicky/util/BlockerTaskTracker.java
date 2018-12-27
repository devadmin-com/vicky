package com.devadmin.vicky.util;

import com.devadmin.jira.Comment;
import com.devadmin.jira.JiraClient;
import com.devadmin.jira.JiraException;
import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.TaskEventModel;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class contain issue comment tracker functionality
 */
public class BlockerTaskTracker {

//  private static final long DELAY = 1000L * 60L; // 1m
//  private static final long PERIOD = 1000L * 60L * 2L; //2m

  private static final long DELAY = 1000L * 60L * 60L * 24L; // 24 h
  private static final long PERIOD = 1000L * 60L * 60L * 6L; // 6h

  private Date startTrackingDate;

  private Timer timer = new Timer();

  private JiraClient jiraClient;

  private MessageService messageService;

  private TaskEventModel taskEventModel;

  private String message;
  private String personName;

  public BlockerTaskTracker(TaskEventModel taskEventModel, String message, JiraClient jiraClient,
      MessageService messageService, String personName) {
    this.jiraClient = jiraClient;
    this.messageService = messageService;
    this.taskEventModel = taskEventModel;
    this.message = message;
    this.personName = personName;
  }

  public void startTracking() {
    startTrackingDate = new Date();
    timer.schedule(new Reminder(), DELAY, PERIOD);
  }

  class Reminder extends TimerTask {

    public void run() {
      try {
        List<Comment> comments = jiraClient.getIssue(taskEventModel.getTask().getId()).getComments();
        if (comments.size() == 0 || startTrackingDate.after(comments.get(comments.size() - 1).getCreatedDate())) {
          messageService.sendPrivateMessage(personName, message);
        } else {
          timer.cancel();
        }
      } catch (JiraException | MessageServiceException e) {
        e.printStackTrace();
      }
    }
  }
}