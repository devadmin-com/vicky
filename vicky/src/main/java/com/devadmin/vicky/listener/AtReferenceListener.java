/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;

import java.util.List;

import com.devadmin.vicky.event.TaskEventModelWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * When a user is referenced in a comment send them a private message
 *
 * <p>Implements story: TL-106 @reference in comment -> slack private message</p>
 */
@Component
public class AtReferenceListener extends TaskToMessageListener {

  //TODO the super class has a LOGGER, why not use it in the sub-classes also?
  private static final Logger LOGGER = LoggerFactory.getLogger(AtReferenceListener.class);

  public AtReferenceListener(MessageService messageService) {
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
