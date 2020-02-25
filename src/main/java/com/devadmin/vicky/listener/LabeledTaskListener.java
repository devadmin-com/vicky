/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.listener;

import com.devadmin.vicky.event.TaskEventModelWrapper;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.model.jira.task.TaskEvent;
import com.devadmin.vicky.model.jira.task.TaskEventType;
import com.devadmin.vicky.service.slack.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * On issue create or resolve, for issues with labels send issue updates to slack channel of that
 * name.
 * <p>
 * <p>Story: TL-127
 */
@Component
@Slf4j
public class LabeledTaskListener extends TaskToMessageListener {

    public LabeledTaskListener(MessageService messageService,
                               @Qualifier("SimpleFormatter") TaskEventFormatter taskEventFormatter,
                               @Value("#{'${slack.notification.task-types.labeledTask}'.split(',')}") List<String> taskTypeIds) {
        super(messageService, taskEventFormatter, taskTypeIds);
    }

    @Override
    public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
        TaskEvent event = eventWrapper.getTaskEventModel();

        if (shouldListenerReactOnEvent(event, eventWrapper)) {
            sendMessage(event);
        } else {
            log.info("Event {} doesn't send notification", eventWrapper.getEventModel());
        }
    }

    private boolean shouldListenerReactOnEvent(TaskEvent event, TaskEventModelWrapper eventWrapper) {
        return event.getType() == TaskEventType.CREATED || event.getTask().isResolved() && !this.shouldSkip(eventWrapper);
    }

    private void sendMessage(TaskEvent event) {
        for (String label : event.getTask().getLabels()) {
            log.info("Trying to send channel message about labeled task");
            messageService.sendChannelMessage(label, formatter.format(event));
        }
    }
}
