package com.devadmin.vicky;

/**
 * Formats a task event for sending into a Message Service...
 */
public interface TaskEventFormatter {
  //TODO javadoc - every interface class should have javadoc for sure.
  String format(TaskEvent task);
}
