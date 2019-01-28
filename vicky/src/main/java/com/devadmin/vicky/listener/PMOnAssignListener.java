/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;
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

    if (event.getChangeLog() != null) { //TODO: what does this mean? wouldn't it be better to have it return an empty changelog list if there are no changes? when would there be no changes though?
      for (ChangeLogItem changeLogItem : event.getChangeLog().getItems()) {
        if (changeLogItem.getChangeType() == ChangeType.ASSIGN ) {

          AssignChangeLogItem assignChangeLogItem = (AssignChangeLogItem) changeLogItem;
          String assignedTo = assignChangeLogItem.getAssignedTo();

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