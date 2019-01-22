package com.devadmin.vicky.util;

import com.devadmin.jira.Comment;
import com.devadmin.jira.JiraClient;
import com.devadmin.jira.JiraException;
import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.TaskEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

/**
 * This class periodically checks if Blocker task contains comments for last 6 hours
 */
public class BlockerTaskTracker {


  private static final long DELAY = 1000L * 60L * 60L * 24L; // 24h
  private static final long PERIOD = 1000L * 60L * 60L * 6L; // 6h

  private Date startTrackingDate;

  private java.util.Timer timer = new java.util.Timer();

  /**
   * Here we need jiraClient because when we received a task event from Jira, this task contains no comments.
   * and using task id via jiraClient we are receiving all comments of the task.
   *
   * Here we must know that the assigned person has added a comment in the task during the last 24 hours,
   * if does not add, we must continue to track every 6 hours.
   * TODO we've talked about this... if it's JIRA specific should be in a JIRA packa, but I think can do without having jira specific stuff here, pleasea refactor so no jira stuff here.
   */
  @Autowired
  private JiraClient jiraClient;

  private MessageService messageService;

  private TaskEvent taskEvent;

  private String message;
  private String personName;

  public BlockerTaskTracker(TaskEvent taskEvent, String message, MessageService messageService, String personName) {
    this.messageService = messageService;
    this.taskEvent = taskEvent;
    this.message = message;
    this.personName = personName;
  }


  /**
   * Start tracking once found task with Blocker priority
   */
  public void startTracking() {
    startTrackingDate = new Date();
    timer.schedule(new Timer(), DELAY, PERIOD);
  }


  /**
   *  calls run method every time after delay
   */
  class Timer extends TimerTask {
    // TODO everytime we restart check last comment time etc...

    public void run() {
      try {
        List<Comment> comments = jiraClient.getIssue(taskEvent.getTask().getId()).getComments();
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