
package com.devadmin.vicky.listener;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.Formatter;
import com.devadmin.vicky.event.TaskEvent;

/**
 * Base class for any listener which takes {@link TaskEvent}s and sends messages to a {@link MessageService}.
 *
 */
public abstract class TaskToMessageListener {

  protected final MessageService messageService;

  protected final Formatter formatter;

  public TaskToMessageListener(MessageService messageService, Formatter formatter) {
    this.messageService = messageService;
    this.formatter = formatter;
  }

}