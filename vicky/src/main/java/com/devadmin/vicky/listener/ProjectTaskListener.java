package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * <p>On issue create or resolve send update to project's channel (if one exists).</p>
 * <p>If no channel exists for project nothing is done.</p>
 * Implements Story: TL-99 issue create, resolve -> project channel
 */
@Component
public class ProjectTaskListener extends TaskToMessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectTaskListener.class);

  @Autowired
  public ProjectTaskListener(MessageService messageService, @Qualifier("SimpleFormatter") TaskEventFormatter taskEventFormatter) {
    super(messageService, taskEventFormatter);
  }

  public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
    TaskEvent event = eventWrapper.getTaskEventModel();

    if (event.getType() == TaskEventType.CREATED) {

      // send to the channel name which is the same as the jira project name
      String channelName = event.getTask().getProject();

      try {
        messageService.sendChannelMessage(channelName, formatter.format(event));
      } catch (MessageServiceException e) {
        LOGGER.error(e.getMessage());
      }
    }
  }
}