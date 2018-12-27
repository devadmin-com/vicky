package com.devadmin.vicky.test;

import com.devadmin.vicky.Task;
import java.util.List;

class TestTask implements Task {

  private final String TEST_PROJECT_NAME = "proj";
  private List<String> labels;

  private String status;
  private String priority;

  @Override
  public String getId() {
    return "1";
  }

  @Override
  public String getDescription() {
    return "description";
  }

  @Override
  public String getPriority() {
    return this.priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  @Override
  public String getType() {
    return null;
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
}
