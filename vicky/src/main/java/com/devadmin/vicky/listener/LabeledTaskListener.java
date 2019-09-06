/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * On issue create or resolve, for issues with labels send issue updates to slack channel of that
 * name.
 * <p>
 * <p>Story: TL-127
 */
@Component
@Slf4j
public class LabeledTaskListener extends TaskToMessageListener {

    public LabeledTaskListener(
            MessageService messageService,
            @Qualifier("SimpleFormatter") TaskEventFormatter taskEventFormatter) {
        super(messageService, taskEventFormatter);
    }

    @Override
    public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {

        TaskEvent event = eventWrapper.getTaskEventModel();
        if (event.getType() == TaskEventType.CREATED || event.getTask().isResolved()) {
            for (String label : event.getTask().getLabels()) {
                try {
                    if (this.shouldSkip(eventWrapper)) {
                        log.info("Trying to send channel message about labeled task");
                        messageService.sendChannelMessage(label, formatter.format(event));
                    } else {
                        log.info("Event {} doesn't send notification", eventWrapper.getEventModel());
                    }
                } catch (MessageServiceException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
