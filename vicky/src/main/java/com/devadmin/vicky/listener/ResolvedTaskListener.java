package com.devadmin.vicky.listener;

import com.devadmin.vicky.*;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * On issue resolve send update to project's channel (if one exists).
 * <p>
 * <p>If no channel exists for project nothing is done. Story: TL-99
 */
@Component
@Slf4j
public class ResolvedTaskListener extends TaskToMessageListener {

    @Autowired
    public ResolvedTaskListener(
            MessageService messageService,
            @Qualifier("SimpleFormatter") TaskEventFormatter taskEventFormatter) {
        super(messageService, taskEventFormatter);
    }

    @Override
    public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
        TaskEvent event = eventWrapper.getTaskEventModel();

        Task task = event.getTask();
        //what we want is just to send notification on resolved task , not a comment
        if (task.isResolved() && event.getType() != null && event.getType().equals(TaskEventType.UPDATED)) {
            String projectName = task.getProject();
            try {
                if (!this.shouldSkip(eventWrapper)) {
                    log.info("Trying to send private message about resolved task");
                    messageService.sendChannelMessage(projectName, formatter.format(event));
                } else {
                    log.info("Event {} doesn't send notification", eventWrapper.getEventModel());
                }
            } catch (MessageServiceException e) {
                log.error(e.getMessage());
            }
        }
    }
}
