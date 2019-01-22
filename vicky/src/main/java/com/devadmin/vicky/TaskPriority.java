package com.devadmin.vicky;

/**
 * This enum contains all possible priorities a task can have
 * TODO: this class looks very different from TaskType, why?
 */
public enum TaskPriority {

  Trivial("Trivial"),
  Minor("Minor"),
  Major("Major"),
  Critical("Critical"),
  Blocker("Blocker");

  String priority;

  TaskPriority(String priority) {
    this.priority = priority;
  }

  public String getPriority() {
    return priority;
  }
}
