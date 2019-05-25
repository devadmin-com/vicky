/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.listener;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * Base class for any listener which takes {@link TaskEventModelWrapper}s and sends messages to a
 * {@link MessageService}.
 */
@Slf4j
public abstract class TaskToMessageListener implements ApplicationListener<TaskEventModelWrapper> {

  final MessageService messageService; // where we write to

  final TaskEventFormatter formatter; // what we use to format tasks

  public TaskToMessageListener(
      MessageService messageService, TaskEventFormatter taskEventFormatter) {
    this.messageService = messageService;
    this.formatter = taskEventFormatter;
  }
}
