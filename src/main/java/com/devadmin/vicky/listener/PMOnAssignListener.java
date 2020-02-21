/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.listener;

import com.devadmin.vicky.event.TaskEventModelWrapper;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.model.jira.FieldModel;
import com.devadmin.vicky.model.jira.JiraEventModel;
import com.devadmin.vicky.model.jira.changelog.AssignChangeLogItem;
import com.devadmin.vicky.model.jira.changelog.ChangeLogItem;
import com.devadmin.vicky.model.jira.changelog.ChangeType;
import com.devadmin.vicky.model.jira.task.IssueModel;
import com.devadmin.vicky.model.jira.task.TaskEvent;
import com.devadmin.vicky.service.slack.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * When a task is assigned to a user, send them a private message. Story TL-105
 */
@Component
@Slf4j
public class PMOnAssignListener extends TaskToMessageListener {

    @Autowired
    public PMOnAssignListener(MessageService messageService,
                              @Qualifier("AssignFormatter") TaskEventFormatter taskEventFormatter,
                              @Value("#{'${slack.notification.task-types.pmOnAssign}'.split(',')}") List<String> taskTypeIds) {
        super(messageService, taskEventFormatter, taskTypeIds);
    }

    @Override
    public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
        TaskEvent event = eventWrapper.getTaskEventModel();
    }

    private boolean shouldListenerReactOnEvent(TaskEvent event, ChangeLogItem changeLogItem) {
        return changeLogItem.getChangeType() == ChangeType.ASSIGN && !isOwnAction(event, (AssignChangeLogItem) changeLogItem);
    }

    private boolean isEventValid(TaskEvent event) {
        return event != null && event.getChangeLog() != null;
    }

    /**
     * @param event               TaskEvent
     * @param assignChangeLogItem Change log
     * @return True if actor and assigned are equal or ticket was unassigned
     */
    private static boolean isOwnAction(final TaskEvent event, final AssignChangeLogItem assignChangeLogItem) {
        return assignChangeLogItem.getAssignedTo() == null || assignChangeLogItem.getAssignedTo().equals(((JiraEventModel) event).getUser().getAccountId());
    }

    private void sendMessage(TaskEventModelWrapper eventWrapper, TaskEvent event) {
        Optional.ofNullable((JiraEventModel) eventWrapper.getEventModel())
                .map(JiraEventModel::getIssue)
                .map(IssueModel::getFields)
                .map(FieldModel::getAssignee)
                .filter(assignee -> assignee.getEmailAddress() != null)
                .ifPresent(assignee -> {
                    log.info("Trying to send private message to {} about assigned task", assignee.getEmailAddress());
                    messageService.sendPrivateMessage(assignee.getEmailAddress(), formatter.format(event));
                });
    }

    private void sendMessages(TaskEventModelWrapper eventWrapper, TaskEvent event) {
        if (isEventValid(event)) {
            for (ChangeLogItem changeLogItem : event.getChangeLog().getItems()) {
                // don't send updates for own actions
                if (shouldListenerReactOnEvent(event, changeLogItem)) {
                    sendMessage(eventWrapper, event);
                }
            }
        }
    }
}

