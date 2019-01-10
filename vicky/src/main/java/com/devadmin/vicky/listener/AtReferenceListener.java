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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * When a user is referenced in a comment send them a private message
 *
 * <p>Implements story: TL-106 @reference in comment -> slack private message</p>
 */
@Component
public class AtReferenceListener extends TaskToMessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(AtReferenceListener.class);


  public AtReferenceListener(MessageService messageService, @Qualifier("SimpleFormatter")  TaskEventFormatter taskEventFormatter) {
    super(messageService, taskEventFormatter);
  }

  public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
    TaskEvent event = eventWrapper.getTaskEventModel();

      List<String> atReferences = event.getComment().getReferences();

      for (String atReference : atReferences) {
        try {
          messageService.sendPrivateMessage(atReference, formatter.format(event));
        } catch (MessageServiceException e) {
          LOGGER.error(e.getMessage());
        }
      }
    }
}
