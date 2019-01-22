/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky;

import java.util.Date;

/**
 * Models an event happening on a task.
 **/
public interface TaskEvent {

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
  TaskEventType getType();

  /**
   *
   * @return the set of changes changelog data
   */
  ChangeLog getChangeLog();

  /**
   * @return comment on task
   */
  Comment getComment();

  /**
   * @return the user who performed the action which caused this event
   */
  String getActor();
}
