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
        if (task.isResolved()) {

            String projectName = task.getProject();

            try {
                log.info("Trying to send private message about resolved task");
                messageService.sendChannelMessage(projectName, formatter.format(event));
            } catch (MessageServiceException e) {
                log.error(e.getMessage());
            }
        }
    }
}
