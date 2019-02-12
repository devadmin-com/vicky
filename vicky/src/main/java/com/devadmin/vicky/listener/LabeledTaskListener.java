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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * On issue create or resolve, for issues with labels send issue updates to slack channel of that
 * name.
 *
 * <p>Story: TL-127
 */
@Component
public class LabeledTaskListener extends TaskToMessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(LabeledTaskListener.class);

  public LabeledTaskListener(
      MessageService messageService,
      @Qualifier("SimpleFormatter") TaskEventFormatter taskEventFormatter) {
    super(messageService, taskEventFormatter);
  }

  @Override
  public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {

    TaskEvent event = eventWrapper.getTaskEventModel();
    if (event.getType() == TaskEventType.CREATED || event.getTask().isResolved()) {
      for (String label : event.getTask().getLabels()) {
        try {
          messageService.sendChannelMessage(label, formatter.format(event));
        } catch (MessageServiceException e) {
          LOGGER.error(e.getMessage());
        }
      }
    }
  }
}
