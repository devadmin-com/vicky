package com.devadmin.vicky.listener;

import com.devadmin.vicky.event.TaskEventModelWrapper;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.model.jira.task.Task;
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
 * On issue resolve send update to project's channel (if one exists).
 * <p>
 * <p>If no channel exists for project nothing is done. Story: TL-99
 */
@Component
@Slf4j
public class ResolvedTaskListener extends TaskToMessageListener {

    @Autowired
    public ResolvedTaskListener(MessageService messageService,
                                @Qualifier("SimpleFormatter") TaskEventFormatter taskEventFormatter,
                                @Value("#{'${slack.notification.task-types.resolvedTask}'.split(',')}") List<String> taskTypeIds) {
        super(messageService, taskEventFormatter, taskTypeIds);
    }

    @Override
    public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
        TaskEvent event = eventWrapper.getTaskEventModel();

        Task task = event.getTask();

        if (shouldListenerReactOnEvent(event, task, eventWrapper)) {
            String projectName = task.getProject();
            log.info("Trying to send private message about resolved task");

            messageService.sendChannelMessage(projectName, formatter.format(event));
        } else {
            log.info("Event {} doesn't send notification", eventWrapper.getEventModel());
        }
    }

    /**
     * What we want is just to send notification on resolved task , also we check if eventWrapper contain all the data or we should skip it
     */
    private boolean shouldListenerReactOnEvent(TaskEvent event, Task task, TaskEventModelWrapper eventWrapper) {
        return task.isResolved() && event.getType() != null
                && event.getType() == TaskEventType.UPDATED
                && !this.shouldSkip(eventWrapper);
    }
}
