package com.devadmin.vicky.test;

import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskPriority;
import com.devadmin.vicky.controller.jira.model.CommentModel;

import java.util.List;

class TestTask implements Task {

  private final String TEST_PROJECT_NAME = "proj";
  private List<String> labels;

  private String status;
  private TaskPriority priority;
  private CommentModel lastComment;

  @Override
  public String getId() {
    return "1";
  }

  @Override
  public String getDescription() {
    return "description";
  }

  @Override
  public TaskPriority getPriority() {
    return this.priority;
  }

  public void setPriority(TaskPriority priority) {
    this.priority = priority;
  }

  @Override
  public String getProject() {
    return TEST_PROJECT_NAME;
  }

  @Override
  public List<String> getLabels() {
    return labels;
  }

  public void setLabels(List<String> labels) {
    this.labels = labels;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @return true if task is resolved
   */
  @Override
  public Boolean isResolved() {
    return "Resolved 解決済".equals(status);
  }

  @Override
  public String getAssignee() {
    return "Test User";
  }

  @Override
  public String getUrl() {
    return "https://devadmin.atlassian.net/browse/TL-000";
  }

  @Override
  public String getSummary() {
    return "Test Task";
  }

  @Override
  public String getKey() {
    return "TL-000";
  }


  public void setLastComment(CommentModel lastComment) {
    this.lastComment = lastComment;
  }

  @Override
  public CommentModel getLastComment() {
    return lastComment;
  }
}
