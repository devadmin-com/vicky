/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/** When a task is assigned to a user, send them a private message. Story TL-105 */
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
      if (changeLogItem.getChangeType() == ChangeType.ASSIGN
          && !event
              .getActor()
              .equals(
                  ((AssignChangeLogItem) changeLogItem)
                      .getAssignedTo())) { // don't send updates for own actions

        AssignChangeLogItem assignChangeLogItem = (AssignChangeLogItem) changeLogItem;
        String assignedTo = assignChangeLogItem.getAssignedTo();
        if (assignedTo != null) {
          try {
            log.info("Trying to send private message to {} about assigned task", assignedTo);
            messageService.sendPrivateMessage(assignedTo, formatter.format(event));
          } catch (MessageServiceException e) {
            log.error(e.getMessage());
          }
        }
      }
    }
  }
}
