package com.devadmin.vicky;

/**
 * Formats a task event for sending into a Message Service...
 */
public interface TaskEventFormatter {
  String format(TaskEvent task);
}
