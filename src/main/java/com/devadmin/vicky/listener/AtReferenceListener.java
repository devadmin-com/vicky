/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.listener;

import com.devadmin.vicky.event.TaskEventModelWrapper;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.model.jira.task.TaskEvent;
import com.devadmin.vicky.service.slack.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * If a user is referenced in a comment send them a private message
 * <p>
 * <p>Story: TL-106
 */
@Component
@Slf4j
public class AtReferenceListener extends TaskToMessageListener {

    @Autowired
    public AtReferenceListener(MessageService messageService, @Qualifier("SimpleFormatter") TaskEventFormatter taskEventFormatter) {
        super(messageService, taskEventFormatter);
    }

    @Override
    public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
        TaskEvent event = eventWrapper.getTaskEventModel();

        if (commentNotEmpty(event)) {
            sendMessageToReferencedPersons(event);
        }
    }

    private boolean isOwnMessage(String atReference, TaskEvent event) {
        return atReference.equals(event.getEventAuthorName());
    }

    private void sendMessageToReferencedPersons(TaskEvent event) {
        List<String> atReferences = event.getComment().getReferences();

        for (String atReference : atReferences) {
            sendMessageToReferencedPerson(atReference, event);
        }
    }

    private void sendMessageToReferencedPerson(String atReference, TaskEvent event) {
        if (!isOwnMessage(atReference, event)) { // don't send updates for comments you write yourself
            log.info("Sending private message to {}, because he was mentioned in comment", atReference);

            messageService.sendPrivateMessage(atReference, formatter.format(event));
        }
    }
}

