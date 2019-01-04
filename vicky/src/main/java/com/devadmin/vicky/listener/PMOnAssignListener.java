/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.listener;

import com.devadmin.jira.JiraClient;
import com.devadmin.vicky.*;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import com.devadmin.vicky.util.BlockerTaskTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * When a task is assigned to a user, send them a private message. (Implements Story TL-105 issue assigned -> slack
 * private message)
 * <p>
 * don't send notification if creator = assignee editor = assignee commenter = assignee mention in comment = assignee
 */
@Component
public class PMOnAssignListener extends TaskToMessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(PMOnAssignListener.class);

  //TODO AAAAAA WHAT IS THIS???? WHY IS IT HERE?!?!?!?  😱⚡☠💩
  private static final String PRIORITYBLOCKER = "Blocker";

  //TODO AAAAAA 😱⚡☠💩 this is fundamentally breaking the abstraction let's discuss ASAP!!!!
  @Autowired
  private JiraClient jiraClient;

  @Autowired
  public PMOnAssignListener(MessageService messageService) {
    super(messageService, taskEventFormatter);
  }

  public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
    TaskEvent event = eventWrapper.getTaskEventModel();
    Task task = event.getTask();

    if (event.getChangeLog() != null) {

      for (ChangeLogItem changeLogItem : event.getChangeLog().getItems()) {
        if (changeLogItem.isAssign() && changeLogItem.getTo() != null) {

          String assignedTo = changeLogItem.getTo();

          if (task.getPriority() !=  TaskPriority.BLOCKER) {

            String blockerMessage = "This message was sent by supercool Vicky 2.0 from Blocker";

            // TODO why is this here? needs docs...
            // TODO why is this not in it's own listener? Why specifically assign events?
            BlockerTaskTracker tracker = new BlockerTaskTracker(model, blockerMessage, jiraClient, messageService,
                assignedTo);
            tracker.startTracking();
          }

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