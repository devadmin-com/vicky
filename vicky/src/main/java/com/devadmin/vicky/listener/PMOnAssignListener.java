/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;
import com.devadmin.vicky.controller.jira.model.FieldModel;
import com.devadmin.vicky.controller.jira.model.IssueModel;
import com.devadmin.vicky.controller.jira.model.JiraEventModel;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * When a task is assigned to a user, send them a private message. Story TL-105
 */
@Component
@Slf4j
public class PMOnAssignListener extends TaskToMessageListener {

    @Autowired
    public PMOnAssignListener(
            MessageService messageService,
            @Qualifier("AssignFormatter") TaskEventFormatter taskEventFormatter) {
        super(messageService, taskEventFormatter);
    }

    @Override
    public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {

        TaskEvent event = eventWrapper.getTaskEventModel();
        for (ChangeLogItem changeLogItem : event.getChangeLog().getItems()) {
            // don't send updates for own actions
            if (changeLogItem.getChangeType() == ChangeType.ASSIGN && !isOwnAction(event, (AssignChangeLogItem) changeLogItem)) {
                if (eventWrapper.getEventModel() instanceof JiraEventModel) {
                    Optional.ofNullable((JiraEventModel) eventWrapper.getEventModel())
                            .map(JiraEventModel::getIssue)
                            .map(IssueModel::getFields)
                            .map(FieldModel::getAssignee)
                            .filter(assignee -> assignee.getEmailAddress() != null)
                            .ifPresent(assignee -> {
                                try {
                                    log.info("Trying to send private message to {} about assigned task", assignee.getEmailAddress());
                                    messageService.sendPrivateMessage(assignee.getEmailAddress(), formatter.format(event));
                                } catch (MessageServiceException e) {
                                    log.error(e.getMessage());
                                }
                            });
                } else {
                    //it was done for testing, I don't know why , need to remove in future
                    try {
                        messageService.sendPrivateMessage("testUser", "my friend");
                    } catch (MessageServiceException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * @param event               TaskEvent
     * @param assignChangeLogItem Change log
     * @return True if actor and assigned are equal or ticket was unassigned
     */
    private static boolean isOwnAction(final TaskEvent event, final AssignChangeLogItem assignChangeLogItem) {
        return assignChangeLogItem.getAssignedTo() == null || assignChangeLogItem.getAssignedTo().equals(event.getActor());
    }
}
