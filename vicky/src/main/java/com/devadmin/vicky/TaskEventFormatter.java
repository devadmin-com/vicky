package com.devadmin.vicky;

/**
 * Formats a task event for sending into a Message Service...
 */
public interface TaskEventFormatter {
  //TODO javadoc - every interface class should have javadoc for sure.

  /**
   * @param taskEvent
   * composes message which have to be send to slack
   */
  String format(TaskEvent taskEvent);
}
