package com.devadmin.vicky.format;


import com.devadmin.vicky.Comment;
import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskEvent;
import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.TaskPriority;
import org.springframework.stereotype.Component;

/**
 * Implements basic formatting of @TaskEventModelWrapper for sending to a @MessageService
 *
 */
@Component("SimpleFormatter")
public class SimpleTaskEventFormatter implements TaskEventFormatter {

  String formatBase(TaskEvent event) {
    Task task = event.getTask();

    return String.format("%s <%s | %s> %s: %s @%s",
        getIcon(task),
        task.getUrl(),
        task.getKey(),
        task.getStatus(),
        task.getSummary(),
        task.getAssignee());
  }

  public String format(TaskEvent event) {
    Task task = event.getTask();

    if (event.getComment() == null){
      return String.format("%s <%s | %s> %s: %s @%s\n %s ➠ %s",
          getIcon(task),
          task.getUrl(),
          task.getKey(),
          task.getStatus(),
          task.getSummary(),
          task.getAssignee(),
          getLastCommenter(task),
          getLastComment(task));
    } else {
      return String.format("%s <%s | %s> %s: %s @%s\n %s ➠ %s",
          getIcon(task),
          task.getUrl(),
          task.getKey(),
          task.getStatus(),
          task.getSummary(),
          task.getAssignee(),
          event.getComment().getAuthor().getDisplayName(),
          event.getComment().getBody());
    }
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

  String getLastCommenter(Task task) {

    Comment comment = task.getLastComment();
    String commenter = comment.getAuthor().getDisplayName();

    return commenter == null ? "Vicky" : commenter;
  }

  String getLastComment(Task task) {

    Comment comment = task.getLastComment();
    String commentBody = comment.getBody();

    return commentBody == null ? "This task does not contain comment" : commentBody;
  }

}
