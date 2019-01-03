/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky;

import java.util.List;

/**
 * A Generic task in some task tracking system...
 *
 * Abstracts away the task tracking system so we can view jira, asana, etc. the same way in our code
 */
public interface Task {

  /**
   * TODO what is a task ID is it "unique identifier of this task within it's task tracking system"?
   * @return task ID
   */
  String getId();

  /**
   * @return this task's description
   */
  String getDescription();

  /**
   * TODO what is this string? how does it abstract between task systems?
   * @return the priority of this task (as a string)
   */
  String getPriority();

  /**
   * @return a string identifier of the project this task belongs to.
   */
  String getProject();

  /**
   * @return the list of labels
   */
  List<String> getLabels();

  /**
   * @return true if task is resolved
   */
  Boolean isResolved();

  /**
   * TODO what is this string status? how does it abstract between task systems?
   * @return status of task
   */
  String getStatus();
}
