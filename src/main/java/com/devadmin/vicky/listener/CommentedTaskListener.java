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

/**
 * If task commented and commenter and assignee is not the same user send assignee PM message
 * <p>
 * <p>Story: TL-108
 */
@Component
@Slf4j
public class CommentedTaskListener extends TaskToMessageListener {

    @Autowired
    public CommentedTaskListener(MessageService messageService, @Qualifier("SimpleFormatter") TaskEventFormatter taskEventFormatter) {
        super(messageService, taskEventFormatter);
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
        return event.getEventAuthorName().equals(event.getTask().getAssignee());
    }

    private void sendPrivateMessage(TaskEvent event) {
        String emailToSent = event.getEmailAuthor();
        log.info("Trying to send private message about commented task to mail {}", emailToSent);

        messageService.sendPrivateMessage(emailToSent, formatter.format(event));
    }
}
