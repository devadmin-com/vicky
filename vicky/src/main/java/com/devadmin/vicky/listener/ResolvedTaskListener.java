
package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * <p>On issue resolve send update to project's channel (if one exists).</p>
 * <p>If no channel exists for project nothing is done.</p>
 */
@Component
public class ResolvedTaskListener extends TaskToMessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResolvedTaskListener.class);

  @Autowired
  public ResolvedTaskListener(MessageService messageService, @Qualifier("SimpleFormatter") TaskEventFormatter taskEventFormatter) {
    super(messageService, taskEventFormatter);
  }

  public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
    TaskEvent event = eventWrapper.getTaskEventModel();

    Task task = event.getTask();
    if (task.isResolved()) {

      // channel name is the same as jira project name
      String channelName = task.getProject();

      try {
        messageService.sendChannelMessage(channelName, formatter.format(event));
      } catch (MessageServiceException e) {
        LOGGER.error(e.getMessage());
      }
    }

  }

}