
package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;
import com.devadmin.vicky.event.*;

import org.springframework.context.event.EventListener;
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

  public ProjectTaskListener(MessageService messageService, Formatter formatter) {
    super(messageService,formatter);

    System.err.println("CONSTRUCTING ProjectTaskListener"); // @todo remove
  }

  public void onApplicationEvent(TaskEvent event) {
    System.err.println("Aaaaaaaa handling");

    if (/* is create or resolve*/true) {
      String msg = formatter.format(event.getTaskEventModel());
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