
package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;
import com.devadmin.vicky.event.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * On issue create or resolve send update to project's channel (if one exists)
 *
 * If no channel exists for project nothing is done.
 *
 * Implements Story: TL-99 issue create, resolve -> project channel
 */
@Component
public class ProjectTaskListener extends TaskToMessageListener {

  @Autowired
  public ProjectTaskListener(MessageService messageService) {
    super(messageService);
  }

  public void onApplicationEvent(TaskEvent event) {
    if (/* is create or resolve*/true) {
      String msg = event.getTaskEventModel().toString();
      String projectId = event.getTaskEventModel().getTask().getProject();

      try {
        messageService.sendChannelMessage(msg, projectId);
      } catch (MessageServiceException e) {
        // @todo handle communications errors differently from "project not found in slack" errors here...
        LOGGER.debug("Couldn't send message for project", projectId);
      }
    }
  }

}