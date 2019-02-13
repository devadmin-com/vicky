package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * On issue create or resolve send update to project's channel (if one exists).
 * <p>If no channel exists for project nothing is done.
 *
 * Story: TL-99
 */
@Component
public class CreatedTaskListener extends TaskToMessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(CreatedTaskListener.class);

  @Autowired
  public CreatedTaskListener(
      MessageService messageService,
      @Qualifier("SimpleFormatter") TaskEventFormatter taskEventFormatter) {
    super(messageService, taskEventFormatter);
  }

  @Override
  public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
    TaskEvent event = eventWrapper.getTaskEventModel();

    if (event.getType() == TaskEventType.CREATED) {
      String projectName = event.getTask().getProject();

      try {
        messageService.sendChannelMessage(projectName, formatter.format(event));
      } catch (MessageServiceException e) {
        LOGGER.error(e.getMessage());
      }
    }
  }
}
