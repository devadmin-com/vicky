
package com.devadmin.vicky.listener;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.TaskEventModelType;
import com.devadmin.vicky.event.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * On issue create or resolve send update to project's channel (if one exists)
 * <p>
 * If no channel exists for project nothing is done.
 * <p>
 * Implements Story: TL-99 issue create, resolve -> project channel
 */
@Component
public class ProjectTaskListener extends TaskToMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectTaskListener.class);

    @Autowired
    public ProjectTaskListener(MessageService messageService) {
        super(messageService);
    }

    @EventListener
    public void onApplicationEvent(TaskEvent event) {

        if (TaskEventModelType.PROJECT_TASK.equals(event.getTaskEventModel().getType())) {

            LOGGER.info(event.toString());

            String message = "This message was sent by supercool Vicky 2.0 from ProjectTaskListener";

            // channel name is the same as jira project name
            String channelName = event.getTask().getProject();


            try {
                messageService.sendChannelMessage(message, channelName);

            } catch (MessageServiceException e) {
                LOGGER.error(e.getMessage());

            }
        }

    }

}