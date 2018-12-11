
package com.devadmin.vicky.listener;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.Formatter;
import com.devadmin.vicky.event.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

/**
 * Base class for any listener which takes {@link TaskEvent}s and sends messages to a {@link MessageService}.
 *
 */
public abstract class TaskToMessageListener implements ApplicationListener<TaskEvent> {

  static final Logger LOGGER = LoggerFactory.getLogger(TaskToMessageListener.class);

  protected MessageService messageService;

  public TaskToMessageListener(MessageService messageService) {
    this.messageService = messageService;
  }

}