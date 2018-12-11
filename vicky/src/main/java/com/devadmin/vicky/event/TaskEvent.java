package com.devadmin.vicky.event;

import com.devadmin.vicky.*;

/**
    An event happened on a {@link Task}...
 */
public class TaskEvent extends GenericEvent<TaskEventModel> {

    public TaskEvent(TaskEventModel model){
        super(model);
    }


    /**
     * @return the event model itself (same as getEventModel but returning concrete class)
     */
    public TaskEventModel getTaskEventModel() {
        return getEventModel();
    }

    /**
     *
     * @return the task for which this event happened
     */
    public Task getTask() {
        return getTaskEventModel().getTask();
    }
}
