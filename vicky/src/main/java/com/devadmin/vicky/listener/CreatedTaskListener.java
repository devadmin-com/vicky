package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * On issue create or resolve send update to project's channel (if one exists).
 * <p>If no channel exists for project nothing is done.
 * <p>
 * Story: TL-99
 */
@Component
@Slf4j
public class CreatedTaskListener extends TaskToMessageListener {

    @Autowired
    public CreatedTaskListener(
            MessageService messageService,
            @Qualifier("SummaryFormatter") TaskEventFormatter taskEventFormatter) {
        super(messageService, taskEventFormatter);
    }

    @Override
    public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
        TaskEvent event = eventWrapper.getTaskEventModel();

        if (event.getType() == TaskEventType.CREATED) {
            String projectName = event.getTask().getProject();

            try {
                messageService.sendChannelMessage(projectName, formatter.format(event));
            } catch (MessageServiceException e) {
                log.error(e.getMessage());
            }
        }
    }
}
