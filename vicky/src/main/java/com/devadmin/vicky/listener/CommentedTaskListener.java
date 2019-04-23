/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.listener;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.TaskEvent;
import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * If task commented and commenter and assignee is not the same user send assignee PM message
 *
 * <p>Story: TL-108
 */
@Component
@Slf4j
public class CommentedTaskListener extends TaskToMessageListener {

  @Autowired
  public CommentedTaskListener(
      MessageService messageService,
      @Qualifier("SimpleFormatter") TaskEventFormatter taskEventFormatter) {
    super(messageService, taskEventFormatter);
  }

  @Override
  public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
    TaskEvent event = eventWrapper.getTaskEventModel();

    if (event.getComment() != null
        && !event
            .getComment()
            .getAuthor()
            .getName()
            .equals(event.getTask().getAssignee())) { // don't send updates for own actions
      try {
        log.info("Trying to send private message about commented task");
        messageService.sendPrivateMessage(
            event.getComment().getAuthor().getName(), formatter.format(event));
      } catch (MessageServiceException e) {
        log.error(e.getMessage());
      }
    }
  }
}
