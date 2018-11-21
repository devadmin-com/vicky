package com.devadmin.jira.webhook;

import com.devadmin.jira.webhook.models.ChangeLog;
import com.devadmin.jira.webhook.models.Comment;
import com.devadmin.jira.webhook.models.Issue;
import com.devadmin.jira.webhook.models.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraWebhookEvent {
  private Long timestamp;

  @JsonProperty("webhookEvent")
  private String webhookEvent;

  @JsonProperty("issue_event_type_name")
  private String issueEventTypeName;

  @JsonProperty("user")
  private User user;

  @JsonProperty("issue")
  private Issue issue;

  @JsonProperty("changelog")
  private ChangeLog changeLog;

  @JsonProperty("comment")
  private Comment comment;

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Issue getIssue() {
    return issue;
  }

  public void setIssue(Issue issue) {
    this.issue = issue;
  }

  public ChangeLog getChangeLog() {
    return changeLog;
  }

  public void setChangeLog(ChangeLog changeLog) {
    this.changeLog = changeLog;
  }

  public Comment getComment() {
    return comment;
  }

  public void setComment(Comment comment) {
    this.comment = comment;
  }
}
