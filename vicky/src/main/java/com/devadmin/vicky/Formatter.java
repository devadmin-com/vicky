package com.devadmin.vicky;

/**
 * Formats a task event for sending into a Message Service...
 */
public interface Formatter {
  String format(TaskEvent task);
}
