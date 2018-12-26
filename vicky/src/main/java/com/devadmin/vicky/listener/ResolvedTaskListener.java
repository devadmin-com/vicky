
package com.devadmin.vicky.listener;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.TaskEventModel;
import com.devadmin.vicky.event.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>On issue resolve send update to project's channel (if one exists).</p>
 * <p>If no channel exists for project nothing is done.</p>
 */
@Component
public class ResolvedTaskListener extends TaskToMessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResolvedTaskListener.class);

  @Autowired
  public ResolvedTaskListener(MessageService messageService) {
    super(messageService);
  }

  public void onApplicationEvent(TaskEvent event) {
    TaskEventModel model = event.getTaskEventModel();

    if (model.getTask().isResolved()) {

      String message = "This message was sent by supercool Vicky 2.0 from ResolvedTaskListener";

      // channel name is the same as jira project name
      String channelName = model.getTask().getProject();

      try {
        messageService.sendChannelMessage(channelName, message);
      } catch (MessageServiceException e) {
        LOGGER.error(e.getMessage());
      }
    }

  }

}