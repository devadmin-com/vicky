/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.listener;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.TaskEvent;

import java.util.List;

import com.devadmin.vicky.event.TaskEventModelWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * On issue create or resolve, for issues with labels send issue updates to slack channel of that name. TL-127 issue
 * with label create, resolve -> #label slack channel
 * <p>
 * If a issue has a label - e.g. server-order - and there is a channel with that name that vicky is part-of (public or
 * private) then issue updates should be sent to this channel. send as: <issue type icon> <Number> (clickable URL)
 * <Status>: <Summary> @<assignee nickname> <commenter name> ➠ <latest comment>
 */
@Component
public class LabeledTaskListener extends TaskToMessageListener {

  //TODO the super class has a LOGGER, why not use it in the sub-classes also?
  private static final Logger LOGGER = LoggerFactory.getLogger(LabeledTaskListener.class);

  public LabeledTaskListener(MessageService messageService) {
    super(messageService, taskEventFormatter);
  }

  public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
    TaskEvent event = eventWrapper.getTaskEventModel();

    List<String> labels = event.getTask().getLabels();

    for (String label : labels) {
      try {
        messageService.sendChannelMessage(label, formatter.format(event));
      } catch (MessageServiceException e) {
        LOGGER.error(e.getMessage());
      }
    }
  }

}