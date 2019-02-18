/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky;

import java.util.List;
import com.devadmin.vicky.controller.jira.model.IssueModel;

/**
 * A generic task service.
 *
 * For now only used for getting list of blocker tasks
 */
public interface TaskService {
  /**
   * @return all open tasks in system which have TaskPriority.BLOCKER
   */
   List<IssueModel> getBlockerTasks();
}