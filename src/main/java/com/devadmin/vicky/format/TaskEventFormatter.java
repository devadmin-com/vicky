package com.devadmin.vicky.format;

import com.devadmin.vicky.model.jira.task.TaskEvent;

/**
 * Formats a task event for sending into a Message Service...
 */
public interface TaskEventFormatter {

    /**
     * @param taskEvent composes message which have to be send to slack
     */
    String format(TaskEvent taskEvent);
}
