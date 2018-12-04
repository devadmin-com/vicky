package com.devadmin.vicky.service.dto.jira;

public class FieldDto {
  private IssueTypeDto issueType;
  private Long timespent;
  private ProjectDto project;
  private String summary;
  private UserDto assignee;
  private StatusDto status;
  private PriorityDto priority;
  private String createdDate;

  public IssueTypeDto getIssueType() {
    return issueType;
  }

  public void setIssueType(IssueTypeDto issueType) {
    this.issueType = issueType;
  }

  public Long getTimespent() {
    return timespent;
  }

  public void setTimespent(Long timespent) {
    this.timespent = timespent;
  }

  public ProjectDto getProject() {
    return project;
  }

  public void setProject(ProjectDto project) {
    this.project = project;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public UserDto getAssignee() {
    return assignee;
  }

  public void setAssignee(UserDto assignee) {
    this.assignee = assignee;
  }

  public StatusDto getStatus() {
    return status;
  }

  public void setStatus(StatusDto status) {
    this.status = status;
  }

  public PriorityDto getPriority() {
    return priority;
  }

  public void setPriority(PriorityDto priority) {
    this.priority = priority;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }
}
