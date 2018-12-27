package com.devadmin.vicky;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class SimpleFormatter implements Formatter {


  @Autowired
  public SimpleFormatter() {

  }

  public String format(TaskEventModel event) {
  /*  String message = new String();
    String cloudUrl = jiraProperties.getJira().getCloudUrl();
    String issueKey = jiraEventModel.getTask().getKey();
    String issueType = issueFields.getIssueType().getName();
    String issuePriority = issueFields.getPriority().getName();

    message.setIssueType(issueType);
    message.setIssueKey(issueKey);
    message.setIssueUrl(String.format("%s/browse/%s", cloudUrl, issueKey));
    message.setIssueStatus(issueFields.getStatus().getName());
    message.setIssueSummary(issueFields.getSummary());
    if (jiraEventModel.getUser() != null)
      message.setIssueCreator(jiraEventModel.getUser().getName());
    message.setIssueProject(issueFields.getProject().getName());
    message.setIssuePriority(issuePriority);
    message.setIssueTypeIcon(getIcon(issuePriority, issueType));
    message.setIssueLabels(issueFields.getLabels());

    if (issueFields.getAssignee() == null){
      message.setIssueAssignedTo("Unassigned");
    } else {
      message.setIssueAssignedTo(issueFields.getAssignee().getName());
    }

    if (jiraEventModel.getChangeLog() != null) {
      for (JiraChangeLogItemModel item : jiraEventModel.getChangeLog().getItems()) {
        if("assignee".equals(item.getField()) && item.getTo() != null){
          message.setIssueAssignedFrom(item.getTo());
        }
      }
    }

    message.setIssueDescription(getIssueDescription(issueFields));

    List<Comment> comments;
    String issueId = jiraEventModel.getTask().getId();
    try {
      comments = jiraClient.getIssue(issueId).getComments();
    } catch (JiraException e) {
      throw new VickyException("Failed to retrieve issue by issueId: " + issueId, e);
    }

    if (jiraEventModel.getComment() == null) {
      if (comments.size() > 0){
        message.setIssueCommenter(comments.get(comments.size() - 1).getAuthor().getDisplayName());
        message.setIssueComment(comments.get(comments.size() - 1).getBody());
      } else {
//        new BlockerTaskTracker(eventDto, message, jiraClient, vickyBot, username);
      }
    } else {
      message.setIssueCommenter(jiraEventModel.getComment().getAuthor().getDisplayName());
      message.setIssueComment(jiraEventModel.getComment().getBody());
    }

    return message;*/

    return event.toString();
  }

  /**
   * Truncates task description for display.
   *
   * @param task the task who's description we want to shorten...
   * @return a shortened version of the task's description
   */
  private String getShortDescription(Task task) {
    StringBuilder stb = new StringBuilder();
    String[] descriptionLines = task.getDescription().split("\r\n|\r|\n");

    // return the first five lines or the whole thing if it's less than 5 lines...
    if (descriptionLines.length > 5) {
      for (int index = 0; index < 5; index++) {
        stb.append(descriptionLines[index]);
      }
    } else {
      stb.append(task.getDescription());
    }

    return stb.toString();
  }

  /**
   * @return the icon to use when displyaing this task
   * @todo this mapping should be in application.yml
   */
  private String getIcon(Task task) {
    if ("Blocker".equals(task.getPriority())) {
      return ":bangbang:";
    } else {
      switch (task.getType()) {
        case "Server サーバー":
          return ":hammer_and_wrench:";
        case "Operations 運営":
          return ":gear:";
        case "Urgent Bug 緊急バグ":
          return ":zap:";
        default:
          return ":rocket:";
      }
    }
  }

}
