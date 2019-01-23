/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import com.devadmin.vicky.util.BlockerTaskTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * TODO: is the comment below correct? looks like same as PMOnAssignListener?
 * When a blocker task is assigned to a user, send them a private message. (Implements Story TL-105 issue assigned ->
 * slack private message)
 * <p>
 * don't send notification if creator = assignee editor = assignee commenter = assignee mention in comment = assignee
 */
@Component
public class BlockerTaskListener extends TaskToMessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(BlockerTaskListener.class);

  @Autowired
  public BlockerTaskListener(MessageService messageService,
      @Qualifier("AssignFormatter") TaskEventFormatter taskEventFormatter) {
    super(messageService, taskEventFormatter);
  }

  public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
    TaskEvent event = eventWrapper.getTaskEventModel();
    Task task = event.getTask();

    if (task.getPriority() == TaskPriority.Blocker) {

      String assignedTo = task.getAssignee();

      String blockerMessage = "This ticket has not been commented more than 24 hours";

      BlockerTaskTracker tracker = new BlockerTaskTracker(event, blockerMessage, messageService, assignedTo);
      tracker.startTracking();
    }
  }
}