package com.devadmin.vicky.format;

import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskEvent;
import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.TaskPriority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implements basic formatting of @TaskEventModelWrapper for sending to a @MessageService
 *
 */
@Component("SimpleFormatter")
public class SimpleTaskEventFormatter implements TaskEventFormatter {


  @Autowired
  public SimpleTaskEventFormatter() {

  }

  public String format(TaskEvent event) {
    Task task = event.getTask();

    StringBuffer message = new StringBuffer(128);


    /* TODO old stuff - remove me!!!!
implement:
 * formats as:
 * <issue type icon> <issue id> (clickable issue URL) <Status>: <Summary> @<assignee nickname>
 * <commenter name> ➠ <latest comment>

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
      }
    } else {
      message.setIssueCommenter(jiraEventModel.getComment().getAuthor().getDisplayName());
      message.setIssueComment(jiraEventModel.getComment().getBody());
    }*/

    return message.toString();

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
   * @return the icon to use when displaying this task
   */
  private String getIcon(Task task) {
    if (task.getPriority() == TaskPriority.BLOCKER) {
      return "‼️";
    } else {
      switch (task.getStatus()) {
        case "Operations 運営":
          return "⚙";
        case "Urgent Bug 緊急バグ":
          return "⚡";
        default:
          return ":rocket:";
      }
    }
  }

}
