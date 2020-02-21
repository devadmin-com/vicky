package com.devadmin.vicky.listener;

import com.devadmin.vicky.event.TaskEventModelWrapper;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.model.jira.task.TaskEvent;
import com.devadmin.vicky.model.jira.task.TaskEventType;
import com.devadmin.vicky.service.slack.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public CreatedTaskListener(MessageService messageService,
                               @Qualifier("SummaryFormatter") TaskEventFormatter taskEventFormatter,
                               @Value("#{'${slack.notification.task-types.createdTask}'.split(',')}") List<String> taskTypeIds) {
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
        return event.getType() == TaskEventType.CREATED && this.shouldSkip(eventWrapper);
    }

    private void sendMessage(TaskEvent event) {
        String projectName = event.getTask().getProject();
        messageService.sendChannelMessage(projectName, formatter.format(event));
    }
}
