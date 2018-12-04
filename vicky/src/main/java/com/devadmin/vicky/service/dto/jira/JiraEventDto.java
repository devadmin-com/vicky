package com.devadmin.vicky.service.dto.jira;

public class JiraEventDto {
  private Long timeStamp;
  private String webhookEvent;
  private String issueEventTypeName;
  private UserDto user;
  private IssueDto issue;
  private ChangeLogDto changeLog;
  private CommentDto comment;

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

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
    this.user = user;
  }

  public IssueDto getIssue() {
    return issue;
  }

  public void setIssue(IssueDto issue) {
    this.issue = issue;
  }

  public ChangeLogDto getChangeLog() {
    return changeLog;
  }

  public void setChangeLog(ChangeLogDto changeLog) {
    this.changeLog = changeLog;
  }

  public CommentDto getComment() {
    return comment;
  }

  public void setComment(CommentDto comment) {
    this.comment = comment;
  }
}
