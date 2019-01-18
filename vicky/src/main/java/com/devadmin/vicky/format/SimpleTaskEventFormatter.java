package com.devadmin.vicky.format;

import com.devadmin.jira.Comment;
import com.devadmin.jira.JiraClient;
import com.devadmin.jira.JiraException;
import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskEvent;
import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.TaskPriority;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implements basic formatting of @TaskEventModelWrapper for sending to a @MessageService
 *
 */
@Component("SimpleFormatter")
public class SimpleTaskEventFormatter implements TaskEventFormatter {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTaskEventFormatter.class);

  @Autowired
  private JiraClient jiraClient;

  protected String formatBase(TaskEvent event) {
    Task task = event.getTask();
    StringBuffer message = new StringBuffer(128);
    message.append(getIcon(task));
    message.append(" <");
    message.append(task.getUrl());
    message.append(" | ");
    message.append(task.getKey());
    message.append("> ");
    message.append(task.getStatus());
    message.append(": ");
    message.append(task.getSummary());
    message.append(" @");
    message.append(task.getAssignee());
    return message.toString();

//    return String.format("%s <%s | %s> %s: %s @%s",
//        getIcon(task),
//        task.getUrl(),
//        task.getKey(),
//        task.getStatus(),
//        task.getSummary(),
//        task.getAssignee());
  }

  public String format(TaskEvent event) {
    Task task = event.getTask();

    StringBuffer message = new StringBuffer(128);
    message.append(formatBase(event));
    message.append("\n");
    message.append(getLastCommenter(task.getId()));
    message.append(" ➠ ");
    message.append(getLastComment(task.getId()));
    return message.toString();

//    return String.format("%s <%s | %s> %s: %s @%s\n %s ➠ %s",
//        getIcon(task),
//        task.getUrl(),
//        task.getKey(),
//        task.getStatus(),
//        task.getSummary(),
//        task.getAssignee(),
//        getLastCommenter(task.getId()),
//        getLastComment(task.getId()));
  }

  /**
   * Truncates task description for display.
   *
   * @param task the task who's description we want to shorten...
   * @return a shortened version of the task's description
   */
  protected String getShortDescription(Task task) {
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
    if (task.getPriority() == TaskPriority.Blocker) {
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

  protected String getLastCommenter(String issueId) {
    List<Comment> comments = getComments(issueId);
    String commenter = null;
    if (comments.size() > 0){
      commenter = comments.get(comments.size() - 1).getAuthor().getDisplayName();
    }
    return commenter == null? "Vicky" : commenter;
  }

  protected String getLastComment(String issueId) {
    List<Comment> comments = getComments(issueId);
    String lastComment = null;
    if (comments.size() > 0){
      lastComment = comments.get(comments.size() - 1).getBody();
    }
    return lastComment == null ? "This task do not contain comment" : lastComment;
  }

  private List<Comment> getComments(String issueId) {
    List<Comment> comments = null;
    try {
      comments = jiraClient.getIssue(issueId).getComments();
    } catch (JiraException e) {
      LOGGER.error("Failed to retrieve issue by issueId: " + issueId, e);
    }
    return comments;
  }

}
