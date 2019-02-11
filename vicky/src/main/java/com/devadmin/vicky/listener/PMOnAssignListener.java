/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.listener;

import com.devadmin.vicky.AssignChangeLogItem;
import com.devadmin.vicky.ChangeLogItem;
import com.devadmin.vicky.ChangeType;
import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.TaskEvent;
import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * When a task is assigned to a user, send them a private message.
 * Story TL-105
 */
@Component
public class PMOnAssignListener extends TaskToMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PMOnAssignListener.class);

    @Autowired
    public PMOnAssignListener(MessageService messageService, @Qualifier("AssignFormatter") TaskEventFormatter taskEventFormatter) {
        super(messageService, taskEventFormatter);
    }

    public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {

        TaskEvent event = eventWrapper.getTaskEventModel();
        for (ChangeLogItem changeLogItem : event.getChangeLog().getItems()) {
          if (changeLogItem.getChangeType() == ChangeType.ASSIGN && !event.getActor()
              .equals(((AssignChangeLogItem) changeLogItem).getAssignedTo())) { // don't send updates for own actions

                AssignChangeLogItem assignChangeLogItem = (AssignChangeLogItem) changeLogItem;
                String assignedTo = assignChangeLogItem.getAssignedTo();
                if (assignedTo != null) {
                    try {
                        messageService.sendPrivateMessage(assignedTo, formatter.format(event));
                    } catch (MessageServiceException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }
        }
    }
}