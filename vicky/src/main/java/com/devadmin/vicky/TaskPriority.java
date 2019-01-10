package com.devadmin.vicky;

/**
 * This enum contains all possible priorities a task can have
 */
public enum TaskPriority {


  NORMAL("NORMAL"), BLOCKER("BLOCKER");

  String priority;

  TaskPriority(String priority) {
    this.priority = priority;
  }

  public String getPriority() {
    return priority;
  }
}
