/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.listener;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.controller.jira.model.FieldModel;
import com.devadmin.vicky.controller.jira.model.IssueModel;
import com.devadmin.vicky.controller.jira.model.IssueTypeModel;
import com.devadmin.vicky.controller.jira.model.JiraEventModel;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Base class for any listener which takes {@link TaskEventModelWrapper}s and sends messages to a
 * {@link MessageService}.
 */
@Slf4j
public abstract class TaskToMessageListener implements ApplicationListener<TaskEventModelWrapper> {

    /**
     * List of ticket types that we skip.
     */
    private static final List<String> SKIP_TICKET_TYPES = Arrays.asList("Task 機能変更・追加", "Bug バグ");

    final MessageService messageService; // where we write to

    final TaskEventFormatter formatter; // what we use to format tasks

    public TaskToMessageListener(
            MessageService messageService, TaskEventFormatter taskEventFormatter) {
        this.messageService = messageService;
        this.formatter = taskEventFormatter;
    }

    /**
     * @param modelWrapper Event wrapper
     * @return True if event should be skipped
     */
    protected boolean shouldSkip(TaskEventModelWrapper modelWrapper) {
        final AtomicBoolean shouldSkip = new AtomicBoolean(false);
        if (modelWrapper.getEventModel() instanceof JiraEventModel) {
            final Optional<String> issueName = Optional.ofNullable(((JiraEventModel) modelWrapper.getEventModel()))
                    .map(JiraEventModel::getIssue)
                    .map(IssueModel::getFields)
                    .map(FieldModel::getIssueType)
                    .map(IssueTypeModel::getName);
            issueName.ifPresent(name -> shouldSkip.set(SKIP_TICKET_TYPES.contains(name)));
        }
        return shouldSkip.get();
    }


}
