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

/**
 * This class contain issue comment tracker functionality
 * TODO what does this mean? I don't understand
 * TODO does this class deserve it's own package? it's just one class... why doesn't it live in vicky.listener where it's used?
 */
public class BlockerTaskTracker {

    //TODO commented out code - BAD BAD BAD, commented out code WITHOUT a TODO - FAIL FAIL FAIL
//  private static final long DELAY = 1000L * 60L; // 1m
//  private static final long PERIOD = 1000L * 60L * 2L; //2m

  private static final long DELAY = 1000L * 60L * 60L * 24L; // 24 h
  private static final long PERIOD = 1000L * 60L * 60L * 6L; // 6h

  private Date startTrackingDate;

  private Timer timer = new Timer();

  //TODO !!! why does this reach into the JIRA client? can it not work on Tasks only? Do we have to couple it to JIRA?
  private JiraClient jiraClient;

  private MessageService messageService;

  private TaskEvent taskEvent;

  private String message;
  private String personName;

  public BlockerTaskTracker(TaskEvent taskEvent, String message, JiraClient jiraClient,
                            MessageService messageService, String personName) {
    this.jiraClient = jiraClient;
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