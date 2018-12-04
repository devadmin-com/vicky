package com.devadmin.vicky.controller.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraEventModel {
  @JsonProperty("timestamp")
  private Long timeStamp;
  @JsonProperty("webhookEvent")
  private String webhookEvent;
  @JsonProperty("issue_event_type_name")
  private String issueEventTypeName;
  @JsonProperty("user")
  private UserModel user;
  @JsonProperty("issue")
  private IssueModel issue;
  @JsonProperty("changelog")
  private ChangeLogModel changeLog;
  @JsonProperty("comment")
  private CommentModel comment;

  public Long getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Long timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getWebhookEvent() {
    return webhookEvent;
  }

  public void setWebhookEvent(String webhookEvent) {
    this.webhookEvent = webhookEvent;
  }

  public String getIssueEventTypeName() {
    return issueEventTypeName;
  }

  public void setIssueEventTypeName(String issueEventTypeName) {
    this.issueEventTypeName = issueEventTypeName;
  }

  public UserModel getUser() {
    return user;
  }

  public void setUser(UserModel user) {
    this.user = user;
  }

  public IssueModel getIssue() {
    return issue;
  }

  public void setIssue(IssueModel issue) {
    this.issue = issue;
  }

  public ChangeLogModel getChangeLog() {
    return changeLog;
  }

  public void setChangeLog(ChangeLogModel changeLog) {
    this.changeLog = changeLog;
  }

  public CommentModel getComment() {
    return comment;
  }

  public void setComment(CommentModel comment) {
    this.comment = comment;
  }
}
