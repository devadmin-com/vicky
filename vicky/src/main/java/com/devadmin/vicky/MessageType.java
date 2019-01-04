/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky;

/**
 * TODO what is this?
 * This enum contain the type of Slack messages
 *
public enum MessageType {
  ISSUE {
    @Override
    public String createMessage(TaskEventModelWrapper taskEventModel) {
      return String.format("%s <%s | %s> %s: %s @%s\n %s ➠ %s",
          taskEventModel.getTask().getIssueTypeIcon(),
          taskEventModel.getTask().getIssueUrl(),
          taskEventModel.getTask().getIssueKey(),
          taskEventModel.getTask().getIssueStatus(),
          taskEventModel.getTask().getIssueSummary(),
          taskEventModel.getTask().getIssueAssignedTo(),
          taskEventModel.getTask().getIssueCommenter(),
          taskEventModel.getTask().getIssueComment());
    }
  },
  ASSIGNEE {
    @Override
    public String createMessage(Task task) {
      return String.format("%s assigned to you: %s <%s | %s> %s: %s @%s\n %s ➠ %s",
          task.getIssueAssignedFrom(),
          task.getIssueTypeIcon(),
          task.getIssueUrl(),
          task.getIssueKey(),
          task.getIssueStatus(),
          task.getIssueSummary(),
          task.getIssueAssignedTo(),
          task.getIssueCommenter(),
          task.getIssueComment());
    }
  },
  COMMENT {
    @Override
    public String createMessage(Task task) {
      return String.format("%s <%s | %s> %s: %s\n %s ➠ %s",
          task.getIssueTypeIcon(),
          task.getIssueUrl(),
          task.getIssueKey(),
          task.getIssueStatus(),
          task.getIssueSummary(),
          task.getIssueCommenter(),
          task.getIssueComment());
    }
  },
  ISSUENUMBER {
    @Override
    public String createMessage(Task task) {
      return String.format("%s <%s | %s> %s: %s\n %s \n%s ➠ %s",
          task.getIssueTypeIcon(),
          task.getIssueUrl(),
          task.getIssueKey(),
          task.getIssueStatus(),
          task.getIssueSummary(),
          task.getIssueDescription(),
          task.getIssueCommenter(),
          task.getIssueComment());
    }
  };

  public abstract String createMessage(Task task);
}
*/