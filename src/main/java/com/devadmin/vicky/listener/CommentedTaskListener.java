/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.listener;

import com.devadmin.vicky.service.EventService;
import com.devadmin.vicky.service.slack.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.model.jira.task.TaskEvent;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * If task commented and commenter and assignee is not the same user send assignee PM message
 * <p>
 * <p>Story: TL-108
 */
@Component
@Slf4j
public class CommentedTaskListener extends TaskToMessageListener {

    private EventService eventService;

    @Autowired
    public CommentedTaskListener(MessageService messageService,
                                 @Qualifier("SimpleFormatter") TaskEventFormatter taskEventFormatter,
                                 @Value("#{'${slack.notification.task-types.commentedTask}'.split(',')}") List<String> taskTypeIds,
                                 EventService eventService) {

        super(messageService, taskEventFormatter, taskTypeIds);
        this.eventService = eventService;
    }

    @Override
    public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
        TaskEvent event = eventWrapper.getTaskEventModel();

        if (shouldSendMessage(event)) {
            sendPrivateMessage(event);
        }
    }

    private boolean shouldSendMessage(TaskEvent event) {
        return commentNotEmpty(event) && !isOwnActions(event);
    }

    private boolean isOwnActions(TaskEvent event) {
        return eventService.getEventAutorName(event).equals(event.getTask().getAssignee());
    }

    private void sendPrivateMessage(TaskEvent event) {
        String emalToSent = event.getEmailAutor();
        log.info("Trying to send private message about commented task to mail {}", emalToSent);

        messageService.sendPrivateMessage(emalToSent, formatter.format(event));
    }
}
