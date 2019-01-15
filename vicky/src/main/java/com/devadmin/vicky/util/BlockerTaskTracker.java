package com.devadmin.vicky.util;

import com.devadmin.jira.Comment;
import com.devadmin.jira.JiraClient;
import com.devadmin.jira.JiraException;
import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.TaskEvent;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class contain issue comment tracker functionality
 * TODO what does this mean? I don't understand
 * TODO does this class deserve it's own package? it's just one class... why doesn't it live in vicky.listener where it's used?
 */
public class BlockerTaskTracker {

    // TODO remove it after testing the functionality
//  private static final long DELAY = 1000L * 60L; // 1m
//  private static final long PERIOD = 1000L * 60L * 2L; //2m

  private static final long DELAY = 1000L * 60L * 60L * 24L; // 24h
  private static final long PERIOD = 1000L * 60L * 60L * 6L; // 6h

  private Date startTrackingDate;

  private Timer timer = new Timer();

  /**
   * Here we need jiraClient because when we received a task event from Jira, this task contains no comments.
   * and using task id via jiraClient we are receiving all comments of the task.
   *
   * Here we must know that the assigned person has added a comment in the task during the last 24 hours,
   * if does not add, we must continue to track every 6 hours.
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

  //TODO javadoc
  public void startTracking() {
    startTrackingDate = new Date();
    timer.schedule(new Reminder(), DELAY, PERIOD);
  }

  //TODO javadoc
  class Reminder extends TimerTask {
      //TODO how will this work when vicky is re-started? i.e. all the timers are lost...

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