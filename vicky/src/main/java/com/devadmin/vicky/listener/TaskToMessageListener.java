/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.listener;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

/**
 * Base class for any listener which takes {@link TaskEventModelWrapper}s and sends messages to a
 * {@link MessageService}.
 */
public abstract class TaskToMessageListener implements ApplicationListener<TaskEventModelWrapper> {

  static final Logger LOGGER = LoggerFactory.getLogger(TaskToMessageListener.class);

  protected MessageService messageService; // where we write to

  protected final TaskEventFormatter formatter; // what we use to format tasks

  public TaskToMessageListener(
      MessageService messageService, TaskEventFormatter taskEventFormatter) {
    this.messageService = messageService;
    this.formatter = taskEventFormatter;
  }
}
