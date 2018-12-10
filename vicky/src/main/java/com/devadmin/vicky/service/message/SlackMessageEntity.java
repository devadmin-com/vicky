package com.devadmin.vicky.service.message;

/**
 * The Entity class for Slack message
 */
public class SlackMessageEntity {

  private String issueType;
  private String issueKey;
  private String issueUrl;
  private String issueStatus;
  private String issueSummary;
  private String issueCommenter;
  private String issueComment;
  private String issueCreator;
  private String issueProject;
  private String issuePriority;
  private String issueTypeIcon;
  private String issueAssignedFrom;
  private String issueAssignedTo;
  private String issueDescription;
  private String[] issueLabels;

  public String getIssueType() {
    return issueType;
  }

  public void setIssueType(String issueType) {
    this.issueType = issueType;
  }

  public String getIssueKey() {
    return issueKey;
  }

  public void setIssueKey(String issueKey) {
    this.issueKey = issueKey;
  }

  public String getIssueUrl() {
    return issueUrl;
  }

  public void setIssueUrl(String issueUrl) {
    this.issueUrl = issueUrl;
  }

  public String getIssueStatus() {
    return issueStatus;
  }

  public void setIssueStatus(String issueStatus) {
    this.issueStatus = issueStatus;
  }

  public String getIssueSummary() {
    return issueSummary;
  }

  public void setIssueSummary(String issueSummary) {
    this.issueSummary = issueSummary;
  }

  public String getIssueCommenter() {
    return issueCommenter;
  }

  public void setIssueCommenter(String issueCommenter) {
    this.issueCommenter = issueCommenter;
  }

  public String getIssueComment() {
    return issueComment;
  }

  public void setIssueComment(String issueComment) {
    this.issueComment = issueComment;
  }

  public String getIssueCreator() {
    return issueCreator;
  }

  public void setIssueCreator(String issueCreator) {
    this.issueCreator = issueCreator;
  }

  public String getIssueProject() {
    return issueProject;
  }

  public void setIssueProject(String issueProject) {
    this.issueProject = issueProject;
  }

  public String getIssuePriority() {
    return issuePriority;
  }

  public void setIssuePriority(String issuePriority) {
    this.issuePriority = issuePriority;
  }

  public String getIssueTypeIcon() {
    return issueTypeIcon;
  }

  public void setIssueTypeIcon(String issueTypeIcon) {
    this.issueTypeIcon = issueTypeIcon;
  }

  public String getIssueAssignedFrom() {
    return issueAssignedFrom;
  }

  public void setIssueAssignedFrom(String issueAssignedFrom) {
    this.issueAssignedFrom = issueAssignedFrom;
  }

  public String getIssueAssignedTo() {
    return issueAssignedTo;
  }

  public void setIssueAssignedTo(String issueAssignedTo) {
    this.issueAssignedTo = issueAssignedTo;
  }

  public String getIssueDescription() {
    return issueDescription;
  }

  public void setIssueDescription(String issueDescription) {
    this.issueDescription = issueDescription;
  }

  public String[] getIssueLabels() {
    return issueLabels;
  }

  public void setIssueLabels(String[] issueLabels) {
    this.issueLabels = issueLabels;
  }
}
