package com.devadmin.vicky;

/**
 * This enum contains all possible priorities a task can have
 */
public enum TaskPriority {
  //TODO: other enums are ALL CAPS, this one is CamelCase, pick one and stick eith it

  Trivial("Trivial"),
  Minor("Minor"),
  Major("Major"),
  Critical("Critical"),
  Blocker("Blocker");

  String priority; // TODO: why do we have this here? please tell me this is not a hard-coding of JIRA stuff...

  TaskPriority(String priority) {
    this.priority = priority;
  }

  public String getPriority() {
    return priority;
  }
}
