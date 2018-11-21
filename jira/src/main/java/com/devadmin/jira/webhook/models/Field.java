package com.devadmin.jira.webhook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Field {
  @JsonProperty("issuetype")
  private IssueType issueType;

  @JsonProperty("timespent")
  private Long timespent;

  @JsonProperty("project")
  private Project project;

  @JsonProperty("fixVersions")
  private List<FixVersion> fixVersions;

  @JsonProperty("summary")
  private String summary;

  @JsonProperty("assignee")
  private User assignee;

  @JsonProperty("status")
  private Status status;

  @JsonProperty("priority")
  private Priority priority;

  @JsonProperty("created")
  private String createdDate;

  public IssueType getIssueType() {
    return issueType;
  }

  public void setIssueType(IssueType issueType) {
    this.issueType = issueType;
  }

  public Long getTimespent() {
    return timespent;
  }

  public void setTimespent(Long timespent) {
    this.timespent = timespent;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public List<FixVersion> getFixVersions() {
    return fixVersions;
  }

  public void setFixVersions(List<FixVersion> fixVersions) {
    this.fixVersions = fixVersions;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public User getAssignee() {
    return assignee;
  }

  public void setAssignee(User assignee) {
    this.assignee = assignee;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Priority getPriority() {
    return priority;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }
}
