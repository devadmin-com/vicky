/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.listener;

import com.devadmin.vicky.service.slack.MessageService;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.model.jira.FieldModel;
import com.devadmin.vicky.model.jira.task.IssueModel;
import com.devadmin.vicky.model.jira.task.IssueTypeModel;
import com.devadmin.vicky.model.jira.JiraEventModel;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

import java.util.List;
import java.util.Optional;

/**
 * Base class for any listener which takes {@link TaskEventModelWrapper}s and sends messages to a
 * {@link MessageService}.
 */
@Slf4j
public abstract class TaskToMessageListener implements ApplicationListener<TaskEventModelWrapper> {

    private List<String> taskTypeIds;
    final MessageService messageService; // where we write to
    final TaskEventFormatter formatter; // what we use to format tasks

    public TaskToMessageListener(MessageService messageService, TaskEventFormatter taskEventFormatter, List<String> taskTypeIds) {
        this.messageService = messageService;
        this.formatter = taskEventFormatter;
        this.taskTypeIds = taskTypeIds;
    }

    /**
     * @param modelWrapper Event wrapper
     * @return True if event should be skipped
     */
    protected boolean shouldSkip(TaskEventModelWrapper modelWrapper) {
        final Optional<String> issueName = Optional.ofNullable(((JiraEventModel) modelWrapper.getEventModel()))
                .map(JiraEventModel::getIssue)
                .map(IssueModel::getFields)
                .map(FieldModel::getIssueType)
                .map(IssueTypeModel::getId);
        return !(issueName.isPresent() && taskTypeIds.contains(issueName.get()));
    }




}
