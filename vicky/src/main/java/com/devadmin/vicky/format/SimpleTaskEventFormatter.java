/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.format;

import com.devadmin.vicky.Comment;
import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskEvent;
import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.TaskPriority;
import org.springframework.stereotype.Component;

/**
 * Implements standard formatting of @TaskEventModelWrapper for sending to a @MessageService
 */
@Component("SimpleFormatter")
public class SimpleTaskEventFormatter implements TaskEventFormatter {

    /**
     * composes basic part of message (without comment)
     */
    protected String formatBase(TaskEvent event) {
        Task task = event.getTask();

        return String.format("%s <%s | %s> %s: %s @%s",
                getIcon(task),
                task.getUrl(),
                task.getKey(),
                task.getStatus(),
                task.getSummary(),
                task.getAssignee());
    }

    /**
     * @param event checks event type (is it issue event or comment event) if task has comment and composes the message
     *              appropriately
     */
    public String format(TaskEvent event) {
        Task task = event.getTask();
        String commenter;
        String lastComment;

            // we are doing this check, because we have two types of event issue and comment,
            // so if getComment is null, then we have issue event and need to extract comments different way
        // TODO: why don't we just move this logic to the getLastCommenter method?
        //TODO: why is this logic not in SummaryTaskEventFormatter?
            if (event.getComment() == null) {
                commenter = getLastCommenter(task);
                lastComment = getLastComment(task); //TODO - why is the comment not truncated?
            } else {
                commenter = event.getComment().getAuthor().getDisplayName();
                lastComment = commentTruncating(event.getComment().getBody()).replace("[~", "@").replace("]", "");
            }

        return String.format("%s\n %s ➠ %s",
                formatBase(event),
                commenter,
                lastComment);
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

    protected String getLastCommenter(Task task) {

        Comment comment = task.getLastComment();
        String commenter = comment.getAuthor().getDisplayName();

        return commenter == null ? "Vicky" : commenter;
    }

    protected String getLastComment(Task task) {

        Comment comment = task.getLastComment();
        String truncatedComment = commentTruncating(comment.getBody());

        return comment.getBody() == null ? "This task does not contain comment"
            : truncatedComment.replace("[~", "@").replace("]", "");
    }

    private String commentTruncating(String text) {
      if (text.length() > 256) {
        String str = text.substring(0, 256);
        return str.substring(0, str.lastIndexOf(' ')) + " ...";
      } else {
        return text;
      }
    }
}
