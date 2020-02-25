/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.format;

import com.devadmin.vicky.model.jira.task.TaskEvent;
import com.devadmin.vicky.model.jira.JiraEventModel;
import org.springframework.stereotype.Component;

/**
 * Implements formatting of assign @TaskEventModelWrapper for sending to a @MessageService
 * <p>
 * Used for task assign actions - adds who assigned to standard message put out by SimpleTaskEventFormatter
 */
@Component("AssignFormatter")
public class AssignTaskEventFormatter extends SimpleTaskEventFormatter {

    @Override
    public String format(TaskEvent event) {
        final String displayName = ((JiraEventModel) event).getUser().getDisplayName();
        return String.format("%s assigned to you: %s", displayName, super.format(event));
    }
}
