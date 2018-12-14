
package com.devadmin.vicky.listener;

import com.devadmin.vicky.Item;
import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.TaskEventModelType;
import com.devadmin.vicky.event.TaskEvent;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(PMOnAssignListener.class);

  @Autowired
  public PMOnAssignListener(MessageService messageService) {
    super(messageService);
  }

  @EventListener
  public void onApplicationEvent(TaskEvent event) {

    if (TaskEventModelType.PM_ON_ASSIGN.equals(event.getTaskEventModel().getType())){

      LOGGER.info(event.toString());

      String message = "This message was sent by supercool Vicky 2.0 from PMOnAssignListener";

      String assignedTo = null;
      List<Item> items = event.getTaskEventModel().getChangeLog().getItems();

      for (Item item : items) {
        if ("assignee".equals(item.getField())) {
          assignedTo = item.getTo();
        }
      }
      try {
        messageService.sendPrivateMessage(message, assignedTo);
      } catch (MessageServiceException e) {
        LOGGER.error(e.getMessage());
      }
    }
  }

}