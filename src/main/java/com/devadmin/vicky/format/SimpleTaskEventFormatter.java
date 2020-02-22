/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.format;

import com.devadmin.vicky.model.jira.comment.Comment;
import com.devadmin.vicky.model.jira.task.Task;
import com.devadmin.vicky.model.jira.task.TaskEvent;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Properties;

/**
 * Implements standard formatting of @TaskEventModelWrapper for sending to a @MessageService
 */
@Component("SimpleFormatter")
public class SimpleTaskEventFormatter implements TaskEventFormatter {

    @Autowired
    @Qualifier("issueTypeIdToIconsMapping")
    private Properties issueTypeIdToIconsMapping;

    private static final int COMMENT_CUT_LENGTH = 256; // all comments are cut off at this length for display
    private static final String DEFAULT_ICON_KEY = "default";
    public static final String EMPTY_COMMENT = "This task does not contain comment";

    /**
     * composes basic part of message (without comment)
     */
    protected String formatBase(TaskEvent event) {
        Task task = event.getTask();
        return String.format(
                "%s <%s | %s> %s: %s @%s",
                getIcon(task),
                task.getUrl(),
                task.getKey(),
                task.getStatus(),
                task.getSummary(),
                task.getAssignee());
    }

    /**
     * @param event checks event type (is it issue event or comment event) if task has comment and
     *              composes the message appropriately
     */
    public String format(TaskEvent event) {
        String commenter = getLastCommenter(event);
        String lastComment = getLastComment(event);

        if (StringUtils.isNotBlank(lastComment)) {
            return String.format("%s\n %s ➠ %s", formatBase(event), commenter, lastComment);
        } else {//task was created without any comments
            return String.format("%s\n ", formatBase(event));
        }
    }

    /**
     * Truncates task description for display.
     *
     * @param event getting task from it which description we want to shorten...
     * @return a shortened version of the task's description
     */
    protected String getShortDescription(TaskEvent event) {
        Task task = event.getTask();
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
    private String getIcon(@NotNull Task task) {
        String icon = issueTypeIdToIconsMapping.getProperty(task.getTypeId());
        if (icon == null) {
            icon = issueTypeIdToIconsMapping.getProperty(DEFAULT_ICON_KEY);
        }
        return icon != null ? icon : "";
    }

    /**
     * @param event which contains task
     * @return commenter of the last comment on task
     */
    protected String getLastCommenter(TaskEvent event) {
        Task task = event.getTask();
        String commenter;

        if (event.getComment() == null) {
            Comment comment = task.getLastComment();
            commenter = comment.getAuthor().getDisplayName();
        } else {
            commenter = event.getComment().getAuthor().getDisplayName();
        }

        return commenter;
    }

    /**
     * @param event which contains task
     * @return last comment on task
     */
    protected String getLastComment(TaskEvent event) {

        Task task = event.getTask();
        String lastComment;

        if (event.getComment() == null) {
            Comment comment = task.getLastComment();

            if (comment == null || comment.getBody() == null) {
                lastComment = EMPTY_COMMENT;
            } else {
                String truncatedComment = truncateComment(comment.getBody());
                lastComment = truncatedComment.replace("[~", "@").replace("]", "");
            }
        } else {
            lastComment = truncateComment(event.getComment().getBody()).replace("[~", "@").replace("]", "");
        }

        return lastComment;
    }

    /**
     * Returns string cut to COMMENT_CUT_LENGTH characters, with "..." at end
     */
    private String truncateComment(String text) {
        if (text.length() > COMMENT_CUT_LENGTH) {
            String str = text.substring(0, COMMENT_CUT_LENGTH);
            return str.substring(0, str.lastIndexOf(' ')) + " ..."; // don't cut word in middle, and always add "..." at end
        } else {
            return text;
        }
    }
}
