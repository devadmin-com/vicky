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

import java.util.List;

/**
 * If a user is referenced in a comment send them a private message
 *
 * <p>Story: TL-106
 */
@Component
@Slf4j
public class AtReferenceListener extends TaskToMessageListener {

  @Autowired
  public AtReferenceListener(
      MessageService messageService,
      @Qualifier("SimpleFormatter") TaskEventFormatter taskEventFormatter) {
    super(messageService, taskEventFormatter);
  }

  @Override
  public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
    TaskEvent event = eventWrapper.getTaskEventModel();

    if (event.getComment() != null) {
      List<String> atReferences = event.getComment().getReferences();

      for (String atReference : atReferences) {
        if (!atReference.equals(
            event
                .getComment()
                .getAuthor()
                .getName())) { // don't send updates for comments you write yourself
          try {
            log.info(
                "Sending private message to {}, because he was mentioned in comment", atReference);
            messageService.sendPrivateMessage(atReference, formatter.format(event));
          } catch (MessageServiceException e) {
            log.error(e.getMessage());
          }
        }
      }
    }
  }
}
