package com.devadmin.vicky;

import com.devadmin.vicky.controller.jira.model.ChangeLogModel;

import java.util.Date;

/**
 * Models an event on a task.
 *
 * <p>(this is used to allow listeners to not care what the implementation of the Task looks like)
 */
public interface TaskEventModel {

  /** @return true if the event has comment */
  boolean hasComment();

  /** @return the task that this event happened on */
  Task getTask();

  /**
   * When the event happened (on the server, not when we received it).
   *
   * @return when the event happened
   */
  Date getTimeStamp();

  /**
   * What type of event happened?
   *
   * @return the type of the event
   */
  TaskEventModelType getType();

  /**
   *
   * @return changelog data
   */
  Changelog getChangeLog();

  /**
   * @return comment on task
   */
  Comment getComment();


}
