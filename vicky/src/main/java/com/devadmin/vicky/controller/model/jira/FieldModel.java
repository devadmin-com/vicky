package com.devadmin.vicky.controller.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * This is the object which contains the information about fields
 * which are describing jira issue
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldModel {
  @JsonProperty("issuetype")
  private IssueTypeModel issueType;
  @JsonProperty("timespent")
  private Long timespent;
  @JsonProperty("project")
  private ProjectModel project;
  @JsonProperty("summary")
  private String summary;
  @JsonProperty("assignee")
  private UserModel assignee;
  @JsonProperty("status")
  private StatusModel status;
  @JsonProperty("priority")
  private PriorityModel priority;
  @JsonProperty("created")
  private String createdDate;
  @JsonProperty("labels")
  private String[] labels;

  public IssueTypeModel getIssueType() {
    return issueType;
  }

  public void setIssueType(IssueTypeModel issueType) {
    this.issueType = issueType;
  }

  public Long getTimespent() {
    return timespent;
  }

  public void setTimespent(Long timespent) {
    this.timespent = timespent;
  }

  public ProjectModel getProject() {
    return project;
  }

  public void setProject(ProjectModel project) {
    this.project = project;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public UserModel getAssignee() {
    return assignee;
  }

  public void setAssignee(UserModel assignee) {
    this.assignee = assignee;
  }

  public StatusModel getStatus() {
    return status;
  }

  public void setStatus(StatusModel status) {
    this.status = status;
  }

  public PriorityModel getPriority() {
    return priority;
  }

  public void setPriority(PriorityModel priority) {
    this.priority = priority;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public String[] getLabels() {
    return labels;
  }

  public void setLabels(String[] labels) {
    this.labels = labels;
  }
}
