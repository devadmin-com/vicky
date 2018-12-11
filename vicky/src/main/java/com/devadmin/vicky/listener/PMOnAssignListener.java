
package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;
import com.devadmin.vicky.event.TaskEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * When a task is assigned to a user, send them a private message.
 *  (Implements Story TL-105 issue assigned -> slack private message)
 *
 * don't send notification if
 * creator = assignee
 * editor = assignee
 * commenter = assignee
 * mention in comment = assignee
 */
@Component
public class PMOnAssignListener extends TaskToMessageListener {

  public PMOnAssignListener(MessageService messageService, Formatter formatter) {
    super(messageService,formatter);
  }

  @EventListener(
      classes = TaskEvent.class
  )
  public void onApplicationEvent(TaskEvent event) {
    System.err.print("Got event!" + event);
  }

}