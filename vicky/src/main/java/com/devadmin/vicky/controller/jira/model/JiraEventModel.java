package com.devadmin.vicky.controller.jira.model;

import com.devadmin.vicky.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;


/**
 * Implements a JIRA Event based on JSON from JIRA webhook
 *
 * This is object contains the information received from jira
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraEventModel implements TaskEventModel {
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

  private TaskEventModelType type;

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

  @Override
  public boolean hasComment() {
    return this.comment != null;
  }

  /**
   * @return the Task that this event was for
   */
  @Override
  public IssueModel getTask() {
    return issue;
  }

  @Override
  public Date getTimeStamp() {
    return new Date(timeStamp);
  }

  @Override
  public TaskEventModelType getType() {
    return type;
  }

  public void setType(TaskEventModelType type) {
    this.type = type;
  }
}
