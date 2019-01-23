/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.format;


import com.devadmin.vicky.*;
import org.springframework.stereotype.Component;

/**
 * Implements basic formatting of @TaskEventModelWrapper for sending to a @MessageService
 */
@Component("SimpleFormatter")
public class SimpleTaskEventFormatter implements TaskEventFormatter {

    /**
     * composes message which is basic for every case
     */
    // TODO: why is this method package visibility?
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


    /**
     * @param event checks event type (is it issue event or comment event) if task has comment and composes the message respectively, which have to be send to slack
     */
    public String format(TaskEvent event) {
        Task task = event.getTask();
        String commenter;
        String lastComment;

        // TODO Discuss this todos with @Victor
        if (event.getComment() == null) { // TODO: why? need one line javadoc why it's doing this check here... also format seems same - so could refactor putting commenter & comment into variable... but why doesn't getLastComment just do that, return the right person?!
            commenter = getLastCommenter(task);
            lastComment = getLastComment(task);
        } else {
            commenter = event.getComment().getAuthor().getDisplayName();
            lastComment = event.getComment().getBody();
        }
        return String.format("%s <%s | %s> %s: %s @%s\n %s ➠ %s",
                getIcon(task),
                task.getUrl(),
                task.getKey(),
                task.getStatus(),
                task.getSummary(),
                task.getAssignee(),
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

    //TODO: javadoc, why package visibility not protected?
    // TODO because package-private is more encapsulated
    String getLastCommenter(Task task) {

        Comment comment = task.getLastComment();
        String commenter = comment.getAuthor().getDisplayName();

        return commenter == null ? "Vicky" : commenter;
    }

    //TODO: javadoc, why package visibility not protected?
    String getLastComment(Task task) {

        Comment comment = task.getLastComment();
        String commentBody = comment.getBody();

        return commentBody == null ? "This task does not contain comment" : commentBody;
    }

}
