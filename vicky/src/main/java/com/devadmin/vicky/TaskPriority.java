package com.devadmin.vicky;

/** This enum contains all possible priorities a task can have */
public enum TaskPriority {
  TRIVIAL("TRIVIAL"),
  MINOR("MINOR"),
  MAJOR("MAJOR"),
  CRITICAL("CRITICAL"),
  BLOCKER("BLOCKER");

  String priority;

  TaskPriority(String priority) {
    this.priority = priority;
  }

  public String getPriority() {
    return priority;
  }
}
