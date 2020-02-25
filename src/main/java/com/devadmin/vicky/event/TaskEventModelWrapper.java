package com.devadmin.vicky.event;

import com.devadmin.vicky.model.jira.task.Task;
import com.devadmin.vicky.model.jira.task.TaskEvent;

/**
 * An event happened on a {@link Task}...
 */
public class TaskEventModelWrapper extends EventModelWrapper<TaskEvent> {

    public TaskEventModelWrapper(TaskEvent model) {
        super(model);
    }

    /**
     * @return the event model itself (same as getEventModel but returning concrete class)
     */
    public TaskEvent getTaskEventModel() {
        return getEventModel();
    }

    /**
     * @return the task for which this event happened
     */
    public Task getTask() {
        return getTaskEventModel().getTask();
    }
}
