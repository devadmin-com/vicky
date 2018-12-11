
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

  @Autowired
  protected MessageService messageService;

  @Autowired
  protected Formatter formatter;

  public TaskToMessageListener(MessageService messageService, Formatter formatter) {
    this.messageService = messageService;
    this.formatter = formatter;
  }

}