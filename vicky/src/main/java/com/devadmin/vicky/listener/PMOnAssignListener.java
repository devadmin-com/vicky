
package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;
import com.devadmin.vicky.event.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * When a task is assigned to a user, send them a private message. (Implements Story TL-105 issue assigned -> slack
 * private message)
 * <p>
 * don't send notification if creator = assignee editor = assignee commenter = assignee mention in comment = assignee
 */
@Component
public class PMOnAssignListener extends TaskToMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PMOnAssignListener.class);

    @Autowired
    public PMOnAssignListener(MessageService messageService) {
        super(messageService);
    }

    public void onApplicationEvent(TaskEvent event) {

        TaskEventModel model = event.getTaskEventModel();

        for (ChangeLogItem changeLogItem : model.getChangeLog().getItems()) {
            if ( changeLogItem.isAssign()) {

                String message = "This message was sent by supercool Vicky 2.0 from PMOnAssignListener";

                String assignedTo = changeLogItem.getTo();

                try {
                    messageService.sendPrivateMessage(message, assignedTo);
                } catch (MessageServiceException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }

}